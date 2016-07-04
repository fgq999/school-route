package com.example.update;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipInputStream;

public class unzip {
	public File root;
	public File zipfile;
	private boolean f=false;


	public boolean unzip() throws Exception {
		// 解压文件不存在时返回
		if (!zipfile.exists()) {
			return false;
		}
		// 释放目录不存时创建
		if (!root.exists()) {
			root.mkdirs();
		}
		// 释放目录不为目录时返回
		if (!root.isDirectory()) {

			return f;
		}
		FileInputStream fin = new FileInputStream(zipfile);
		ZipInputStream zin = new ZipInputStream(fin);
		java.util.zip.ZipEntry entry = null;
		while ((entry =  zin.getNextEntry()) != null) {
			//   if (!entry.getName().endsWith(file)) {
//	    continue;
			//   }
			File tmp = new File(root, entry.getName());
			if (entry.isDirectory()) {
				tmp.mkdirs();
			} else {
				byte[] buff = new byte[4096];
				int len = 0;
				tmp.getParentFile().mkdirs();
				FileOutputStream fout = new FileOutputStream(tmp);
				while ((len = zin.read(buff)) != -1) {
					fout.write(buff, 0, len);
				}
				zin.closeEntry();
				fout.close();
				System.out.println("解压成功");
				f=true;



			}
		}
		return f;
	}

}
