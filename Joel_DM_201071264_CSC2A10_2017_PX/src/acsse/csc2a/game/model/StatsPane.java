/**
 * 
 */
package acsse.csc2a.game.model;

import java.io.Serializable;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @author Joel, DM, 201071264
 *
 */
public class StatsPane extends VBox {
	Label lblDay;
	Label lblPop;
	Label lblPopChange;
	Label lblTech;
	Label lblTechChange;
	Label lblAir;
	Label lblAirChange;
	Label lblPower;
	Label lblPowerChange;
	Label lblFood;
	Label lblFoodChange;
	Label lblEntertainment;
	Label lblEnterPerCapita;
	Label lblMaterial;
	Label lblMaterialChanged;
	Label lblHouses;
	Label lblPeoplePerHouse;
	
	TextField txtDay;
	TextField txtPop;
	TextField txtPopChange;
	TextField txtTech;
	TextField txtTechChange;
	TextField txtAir;
	TextField txtAirChange;
	TextField txtPower;
	TextField txtPowerChange;
	TextField txtFood;
	TextField txtFoodChange;
	TextField txtEntertainment;
	TextField txtEnterPerCapita;
	TextField txtMaterial;
	TextField txtMaterialChanged;
	TextField txtHouses;
	TextField txtPeopleperHouse;
	
	HBox hDay;
	HBox hPop;
	HBox hTech;
	HBox hAir;
	HBox hPower;
	HBox hFood;
	HBox hEntertainment;
	HBox hMaterial;
	HBox hHouses;
	
	public StatsPane() {
		lblDay = new Label("Day:");
		lblPop = new Label("Population:");
		lblPopChange = new Label("Population Births/Deaths:");
		lblTech = new Label("Tech:");
		lblTechChange = new Label("Tech Used/Created:");
		lblAir = new Label("Air:");
		lblAirChange = new Label ("Air Used/Created:");
		lblPower = new Label("Power:");
		lblPowerChange = new Label("Power Used/Created:");
		lblFood = new Label("Food:");
		lblFoodChange = new Label("Food Consumed/Grown:");
		lblEntertainment = new Label("Entertainment:");
		lblEnterPerCapita = new Label("Entertainment / Colonist:");
		lblMaterial = new Label("Material:");
		lblMaterialChanged = new Label("Material Used/Mined:");
		lblHouses = new Label("Houses:");
		lblPeoplePerHouse = new Label("Colonists per House:");
		
		txtDay = new TextField();
		txtPop = new TextField();
		txtPopChange = new TextField();
		txtTech = new TextField();
		txtTechChange = new TextField();
		txtAir = new TextField();
		txtAirChange = new TextField();
		txtPower = new TextField();
		txtPowerChange = new TextField();
		txtFood = new TextField();
		txtFoodChange = new TextField();
		txtEntertainment = new TextField();
		txtEnterPerCapita = new TextField();
		txtMaterial = new TextField();
		txtMaterialChanged = new TextField();
		txtHouses = new TextField();
		txtPeopleperHouse = new TextField();
		
		txtDay.setEditable(false);
		txtPop.setEditable(false);
		txtPopChange.setEditable(false);
		txtTech.setEditable(false);
		txtTechChange.setEditable(false);
		txtAir.setEditable(false);
		txtAirChange.setEditable(false);
		txtPower.setEditable(false);
		txtPowerChange.setEditable(false);
		txtFood.setEditable(false);
		txtFoodChange.setEditable(false);
		txtEntertainment.setEditable(false);
		txtEnterPerCapita.setEditable(false);
		txtMaterial.setEditable(false);
		txtMaterialChanged.setEditable(false);
		txtHouses.setEditable(false);
		txtPeopleperHouse.setEditable(false);
		
		hDay = new HBox();
		hDay.getChildren().addAll(lblDay, txtDay);
		hDay.setSpacing(5);
		hPop = new HBox();
		hPop.getChildren().addAll(lblPop, txtPop, lblPopChange, txtPopChange);
		hPop.setSpacing(5);
		hTech = new HBox();
		hTech.getChildren().addAll(lblTech, txtTech, lblTechChange, txtTechChange);
		hTech.setSpacing(5);
		hAir = new HBox();
		hAir.getChildren().addAll(lblAir, txtAir, lblAirChange, txtAirChange);
		hAir.setSpacing(5);
		hPower = new HBox();
		hPower.getChildren().addAll(lblPower, txtPower, lblPowerChange, txtPowerChange);
		hPower.setSpacing(5);
		hFood = new HBox();
		hFood.getChildren().addAll(lblFood, txtFood, lblFoodChange, txtFoodChange);
		hFood.setSpacing(5);
		hEntertainment = new HBox();
		hEntertainment.getChildren().addAll(lblEntertainment, txtEntertainment, lblEnterPerCapita, txtEnterPerCapita);
		hEntertainment.setSpacing(5);
		hMaterial = new HBox();
		hMaterial.getChildren().addAll(lblMaterial, txtMaterial);
		hMaterial.setSpacing(5);
		hHouses = new HBox();
		hHouses.getChildren().addAll(lblHouses, txtHouses, lblPeoplePerHouse, txtPeopleperHouse);
		hHouses.setSpacing(5);
		
		double dblBoxHeight = hDay.getHeight();
		double dblSpacingHeight = (getHeight() - (dblBoxHeight*7)) / 7;
		
		System.out.println(dblSpacingHeight);
		getChildren().addAll(hDay, hPop, hHouses, hTech, hMaterial, hAir, hPower, hFood, hEntertainment);
		setSpacing(5);
	}

	/**
	 * @return the txtDay
	 */
	public TextField getTxtDay() {
		return txtDay;
	}

	/**
	 * @param txtDay the txtDay to set
	 */
	public void setTxtDay(TextField txtDay) {
		this.txtDay = txtDay;
	}

	/**
	 * @return the txtPop
	 */
	public TextField getTxtPop() {
		return txtPop;
	}

	/**
	 * @param txtPop the txtPop to set
	 */
	public void setTxtPop(TextField txtPop) {
		this.txtPop = txtPop;
	}

	/**
	 * @return the txtPopChange
	 */
	public TextField getTxtPopChange() {
		return txtPopChange;
	}

	/**
	 * @param txtPopChange the txtPopChange to set
	 */
	public void setTxtPopChange(TextField txtPopChange) {
		this.txtPopChange = txtPopChange;
	}

	/**
	 * @return the txtTech
	 */
	public TextField getTxtTech() {
		return txtTech;
	}

	/**
	 * @param txtTech the txtTech to set
	 */
	public void setTxtTech(TextField txtTech) {
		this.txtTech = txtTech;
	}

	/**
	 * @return the txtTechChange
	 */
	public TextField getTxtTechChange() {
		return txtTechChange;
	}

	/**
	 * @param txtTechChange the txtTechChange to set
	 */
	public void setTxtTechChange(TextField txtTechChange) {
		this.txtTechChange = txtTechChange;
	}

	/**
	 * @return the txtAir
	 */
	public TextField getTxtAir() {
		return txtAir;
	}

	/**
	 * @param txtAir the txtAir to set
	 */
	public void setTxtAir(TextField txtAir) {
		this.txtAir = txtAir;
	}

	/**
	 * @return the txtAirChange
	 */
	public TextField getTxtAirChange() {
		return txtAirChange;
	}

	/**
	 * @param txtAirChange the txtAirChange to set
	 */
	public void setTxtAirChange(TextField txtAirChange) {
		this.txtAirChange = txtAirChange;
	}

	/**
	 * @return the txtPower
	 */
	public TextField getTxtPower() {
		return txtPower;
	}

	/**
	 * @param txtPower the txtPower to set
	 */
	public void setTxtPower(TextField txtPower) {
		this.txtPower = txtPower;
	}

	/**
	 * @return the txtPowerChange
	 */
	public TextField getTxtPowerChange() {
		return txtPowerChange;
	}

	/**
	 * @param txtPowerChange the txtPowerChange to set
	 */
	public void setTxtPowerChange(TextField txtPowerChange) {
		this.txtPowerChange = txtPowerChange;
	}

	/**
	 * @return the txtFood
	 */
	public TextField getTxtFood() {
		return txtFood;
	}

	/**
	 * @param txtFood the txtFood to set
	 */
	public void setTxtFood(TextField txtFood) {
		this.txtFood = txtFood;
	}

	/**
	 * @return the txtFoodChange
	 */
	public TextField getTxtFoodChange() {
		return txtFoodChange;
	}

	/**
	 * @param txtFoodChange the txtFoodChange to set
	 */
	public void setTxtFoodChange(TextField txtFoodChange) {
		this.txtFoodChange = txtFoodChange;
	}

	/**
	 * @return the txtEntertainment
	 */
	public TextField getTxtEntertainment() {
		return txtEntertainment;
	}

	/**
	 * @param txtEntertainment the txtEntertainment to set
	 */
	public void setTxtEntertainment(TextField txtEntertainment) {
		this.txtEntertainment = txtEntertainment;
	}

	/**
	 * @return the txtEnterPerCapita
	 */
	public TextField getTxtEnterPerCapita() {
		return txtEnterPerCapita;
	}

	/**
	 * @param txtEnterPerCapita the txtEnterPerCapita to set
	 */
	public void setTxtEnterPerCapita(TextField txtEnterPerCapita) {
		this.txtEnterPerCapita = txtEnterPerCapita;
	}

	/**
	 * @return the txtHouses
	 */
	public TextField getTxtHouses() {
		return txtHouses;
	}

	/**
	 * @param txtHouses the txtHouses to set
	 */
	public void setTxtHouses(TextField txtHouses) {
		this.txtHouses = txtHouses;
	}

	/**
	 * @return the txtPeopleperHouse
	 */
	public TextField getTxtPeopleperHouse() {
		return txtPeopleperHouse;
	}

	/**
	 * @param txtPeopleperHouse the txtPeopleperHouse to set
	 */
	public void setTxtPeopleperHouse(TextField txtPeopleperHouse) {
		this.txtPeopleperHouse = txtPeopleperHouse;
	}

	/**
	 * @return the txtMaterial
	 */
	public TextField getTxtMaterial() {
		return txtMaterial;
	}

	/**
	 * @param txtMaterial the txtMaterial to set
	 */
	public void setTxtMaterial(TextField txtMaterial) {
		this.txtMaterial = txtMaterial;
	}

	/**
	 * @return the txtMaterialChanged
	 */
	public TextField getTxtMaterialChanged() {
		return txtMaterialChanged;
	}

	/**
	 * @param txtMaterialChanged the txtMaterialChanged to set
	 */
	public void setTxtMaterialChanged(TextField txtMaterialChanged) {
		this.txtMaterialChanged = txtMaterialChanged;
	}

}
