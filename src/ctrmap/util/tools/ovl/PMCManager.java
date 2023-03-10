package ctrmap.util.tools.ovl;

import ctrmap.formats.ntr.rom.OverlayTable;
import ctrmap.missioncontrol_ntr.fs.NTRGameFS;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import rpm.elfconv.ESDBSegmentInfo;
import rpm.elfconv.ExternalSymbolDB;
import rpm.format.rpm.RPM;
import rpm.format.rpm.RPMSymbol;
import rpm.util.AutoRelGenerator;
import xstandard.fs.FSFile;
import xstandard.io.base.impl.ext.data.DataIOStream;
import xstandard.math.MathEx;

public class PMCManager {
	
	private static final int PMC_OVERLAY_SIZE = 0x3000;
	private static final String PMC_RPM_UID = "PMC.rpm";

	private final NTRGameFS fs;
	private CodeInjectionSystem cis;
	private final ExternalSymbolDB esdb;

	public PMCManager(NTRGameFS fs) {
		this.fs = fs;
		esdb = genESDB(fs);
	}
	
	private static ExternalSymbolDB genESDB(NTRGameFS fs) {
		ExternalSymbolDB esdb = new ExternalSymbolDB();
		int id = 0;
		esdb.putSegment(new ESDBSegmentInfo(id, "ARM9", AutoRelGenerator.NTRSegmentType.EXECUTABLE.name()));
		id++;
		int ovlCount = fs.getARM9OverlayTable().overlays.size();
		for (int ovlId = 0; ovlId < ovlCount; ovlId++) {
			esdb.putSegment(new ESDBSegmentInfo(id, String.valueOf(ovlId), AutoRelGenerator.NTRSegmentType.OVERLAY.name()));
			id++;
		}
		return esdb;
	}

	private void ensureCIS() {
		if (cis == null) {
			boolean isNew = false;
			int overlayId = getPMCOverlayId();
			int baseAddress;
			if (overlayId == -1) {
				OverlayTable ovlTable = fs.getARM9OverlayTable();
				overlayId = ovlTable.overlays.size();
				
				/*baseAddress = fs.getARM9LoadAddr() + fs.getDecompressedArm9BinMaybeRO().length();
				for (OverlayTable.OverlayInfo info : ovlTable.overlays) {
					int end = info.mountAddress + info.mountSize + info.bssSize;
					if (end > baseAddress) {
						baseAddress = end;
					}
				}*/
				baseAddress = 0x021FD0C0; //fixed at the moment since we have yet to adjust the symbols within the patch that actually notify the game of this
				
				writePMCOverlayId(overlayId);
				isNew = true;
			}
			else {
				baseAddress = fs.getOvlLoadAddr(overlayId);
			}
			System.out.println("PMCManager init @ overlay " + overlayId + " | Base address 0x" + Integer.toHexString(baseAddress) + ", buffer size 0x" + Integer.toHexString(PMC_OVERLAY_SIZE));
			cis = new CodeInjectionSystem(fs, esdb, new CodeInjectionSetup(overlayId, baseAddress, PMC_OVERLAY_SIZE));
			if (isNew) {
				//No longer need ARM9 decompression patch since our ROM packer handles that automatically
				//cis.queueFilePatch(new MemoryFile("arm9_decmp_off_HK_REL.rpm", CTRMapVResources.ACCESSOR.getByteArray("codeinjection/arm9_decmp_off_HK_REL.rpm")));
				cis.savePSysData();
			}
		}
	}

	public void updatePMC(RPM pmcRPM) {
		ensureCIS();
		setOverlaySizeToPMC(pmcRPM);
		cis.queueRPMPatch(pmcRPM, PMC_RPM_UID);
		cis.savePSysData();
	}
	
	private void setOverlaySizeToPMC(RPM pmcRPM) {
		RPMSymbol symb = pmcRPM.getSymbol("FULL_COPY_ARM9_0x0207B41C_ResizeMemoryForOvl344");
		if (symb != null) {
			try {
				DataIOStream code = pmcRPM.getCodeStream();
				code.seekUnbased(symb.address);
				int heapStart = MathEx.padInteger(0x021FD0C0 + fs.getARM9OverlayTable().getOverlayInfo(getPMCOverlayId()).mountSize, 0x4);
				code.writeInt(heapStart);
				System.out.println("Set system heap start to 0x" + Integer.toHexString(heapStart));
			} catch (IOException ex) {
				Logger.getLogger(PMCManager.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		else {
			System.err.println("Could not find overlay size symbol in PMC!");
		}
	}

	private FSFile getOverlayIdTxt() {
		return fs.getDataFile(CodeInjectionSystem.CISYS_ROMFS_DIR + "/pmc_overlay.txt");
	}

	private void writePMCOverlayId(int overlayId) {
		FSFile file = getOverlayIdTxt();
		if (file.isDirectory()) {
			file.delete();
		}
		file.setBytes(String.valueOf(overlayId).getBytes(StandardCharsets.US_ASCII));
	}

	private int getPMCOverlayId() {
		try {
			FSFile file = getOverlayIdTxt();
			if (file != null && file.isFile()) {
				String data = new String(file.getBytes(), StandardCharsets.US_ASCII);
				return Integer.parseInt(data);
			}
		} catch (Exception ex) {

		}
		return -1;
	}
}
