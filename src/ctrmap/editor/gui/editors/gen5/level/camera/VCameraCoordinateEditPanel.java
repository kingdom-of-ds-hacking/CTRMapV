package ctrmap.editor.gui.editors.gen5.level.camera;

import xstandard.gui.components.ComponentUtils;
import xstandard.math.vec.Vec3f;
import ctrmap.formats.pokemon.gen5.camera.VCameraCoordinates;

public class VCameraCoordinateEditPanel extends javax.swing.JPanel {

	private VCameraCoordinates coords;

	public VCameraCoordinateEditPanel() {
		initComponents();

		ComponentUtils.setNFValueClass(Float.class, plrDist, pitch, yaw, fov);
	}

	public void showCoords(VCameraCoordinates coords) {
		this.coords = coords;

		reloadPitchYaw();
		reloadFOV();
		reloadDist();
		camTgtOff.loadVec(coords == null ? Vec3f.ZERO() : coords.targetOffset);
	}

	public void saveTo(VCameraCoordinates coords) {
		VCameraCoordinates old = this.coords;
		this.coords = coords;
		save();
		this.coords = old;
	}
	
	public void save() {
		if (coords != null) {
			coords.pitch = (Float) pitch.getValue();
			coords.yaw = (Float) yaw.getValue();
			coords.FOV = (Float) fov.getValue();
			coords.tz = (Float) plrDist.getValue();
		}
	}

	public void reloadPitchYaw() {
		if (coords != null) {
			pitch.setValue(coords.pitch);
			yaw.setValue(coords.yaw);
		}
	}

	public void reloadFOV() {
		if (coords != null) {
			fov.setValue(coords.FOV);
		}
	}

	public void reloadDist() {
		if (coords != null) {
			plrDist.setValue(coords.tz);
		}
	}
	
	public void reloadTargetOffset(){
		camTgtOff.refresh();
	}

	/**
	 * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fov1Deg = new javax.swing.JLabel();
        plrDist = new javax.swing.JFormattedTextField();
        plrDist1Label = new javax.swing.JLabel();
        fov = new javax.swing.JFormattedTextField();
        yaw1deg = new javax.swing.JLabel();
        pitch1deg = new javax.swing.JLabel();
        fov1Label = new javax.swing.JLabel();
        yaw = new javax.swing.JFormattedTextField();
        yaw1Label = new javax.swing.JLabel();
        pitch = new javax.swing.JFormattedTextField();
        pitch1Label = new javax.swing.JLabel();
        camTgtOffLabel = new javax.swing.JLabel();
        camTgtOff = new xstandard.gui.components.Vec3fEditor();

        fov1Deg.setText("°");

        plrDist.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));

        plrDist1Label.setText("Distance from player");

        fov.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));

        yaw1deg.setText("°");

        pitch1deg.setText("°");

        fov1Label.setText("FOV");

        yaw.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));

        yaw1Label.setText("Yaw");

        pitch.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));

        pitch1Label.setText("Pitch");

        camTgtOffLabel.setText("Camera target offset");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(yaw1Label, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pitch1Label))
                        .addGap(7, 7, 7)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pitch, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(yaw, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pitch1deg)
                            .addComponent(yaw1deg)))
                    .addComponent(camTgtOffLabel)
                    .addComponent(camTgtOff, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(plrDist1Label)
                            .addComponent(plrDist, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(fov, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(fov1Deg))
                            .addComponent(fov1Label))))
                .addContainerGap(45, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pitch1Label)
                    .addComponent(pitch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pitch1deg))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(yaw1Label)
                    .addComponent(yaw, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(yaw1deg))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(plrDist1Label)
                    .addComponent(fov1Label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(plrDist, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fov, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fov1Deg))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(camTgtOffLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(camTgtOff, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private xstandard.gui.components.Vec3fEditor camTgtOff;
    private javax.swing.JLabel camTgtOffLabel;
    private javax.swing.JFormattedTextField fov;
    private javax.swing.JLabel fov1Deg;
    private javax.swing.JLabel fov1Label;
    private javax.swing.JFormattedTextField pitch;
    private javax.swing.JLabel pitch1Label;
    private javax.swing.JLabel pitch1deg;
    private javax.swing.JFormattedTextField plrDist;
    private javax.swing.JLabel plrDist1Label;
    private javax.swing.JFormattedTextField yaw;
    private javax.swing.JLabel yaw1Label;
    private javax.swing.JLabel yaw1deg;
    // End of variables declaration//GEN-END:variables
}
