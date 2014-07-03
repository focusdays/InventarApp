package com.example.inventoryappbase.core.image;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;

public enum Size {
	THUMB ("thumb", 300),
	SMALL ("small", 512),
	MEDIUM ("medium", 1024),
	LARGE ("large", 2048),
	ORIGINAL ("original", -1),
	;
	
	private static Map<String, Size> IMAGE_MAP = new HashMap<String, Size>();
	private static Size[] SHRINKABLE_LIST = {THUMB, SMALL, MEDIUM, LARGE };
	
	private String name;
	private int width;
	
	Size(String name, int width) {
		this.name = name;
		this.width = width;
	}
	public String getName() {
		return this.name;
	}
	public int getWidth() {
		return this.width;
	}
	
	/* return new fileName based on current width
	 * 
	 */
	public String addToFileName(String fileName) {
		if (this == ORIGINAL) return fileName;
		String ext = FilenameUtils.getExtension(fileName);
		return FilenameUtils.removeExtension(fileName)+"-$"+this.getWidth()+'.'+ext;
	}
	public String removeFromFileName(String fileName) {
		String fileNameNoExt = FilenameUtils.removeExtension(fileName);
		int lastIndex = fileNameNoExt.lastIndexOf("-$");
		if (lastIndex < 0) return fileName;
		String ext = FilenameUtils.getExtension(fileName);
		return fileNameNoExt.substring(0, lastIndex)+'.'+ext;
	}
	
	public static Size fromFileName(String fileName) {
		String fileNameNoExt = FilenameUtils.removeExtension(fileName);
		int lastIndex = fileNameNoExt.lastIndexOf("-$");
		if (lastIndex < 0) return ORIGINAL;
		return Size.valueOfWidth(fileNameNoExt.substring(lastIndex+2));
	}
	public static boolean isValidSize(String size) {
		for (Size s : Size.values()) {
			if (s.getName().equalsIgnoreCase(size.toLowerCase())) return true;
		}
		return false;
	}
	public static Size valueSizeOf(String size) {
		for (Size s : Size.values()) {
			if (s.getName().equalsIgnoreCase(size.toLowerCase())) return s;
		}
		return null;
	}
	public static Size valueOfWidth(int width) {
		for (Size s : Size.values()) {
			if (s.getWidth() == width) return s;
		}
		return null;
	}
	public static Size valueOfWidth(String width) {
		int w = Integer.parseInt(width);
		for (Size s : Size.values()) {
			if (s.getWidth() == w) return s;
		}
		return null;
	}
	public static Size[] shrinkableValues() {
		return SHRINKABLE_LIST;
	}
	
	static {
		IMAGE_MAP.put(THUMB.getName(), THUMB);
		IMAGE_MAP.put(SMALL.getName(), SMALL);
		IMAGE_MAP.put(MEDIUM.getName(), MEDIUM);
		IMAGE_MAP.put(LARGE.getName(), LARGE);
		IMAGE_MAP.put(ORIGINAL.getName(), ORIGINAL);
	}
}
