package com.example.inventoryapp.model;

public enum CommodityType {
	Moebel(1), Schmuck(2), Wertpapiere(3), Kleidung(4), Kunst(5), Elektronik(6), Buecher(
			7), Antiquitaeten(8), Hobby(9), Accessoires(10), Handwerk(11), Sport(
			12), Kind(13), Tierzubehoer(14), Seltenes(15);

	final int value;

	public int getValue() {
		return this.value;
	}

	private CommodityType(int value) {
		this.value = value;
	}

}
