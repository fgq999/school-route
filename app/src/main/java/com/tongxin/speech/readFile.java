//FileHelper.java
/**
 * @Title: FileHelper.java
 * @Package com.tes.textsd
 * @Description: TODO(用一句话描述该文件做什么)
 * @author Alex.Z
 * @date 2013-2-26 下午5:45:40
 * @version V1.0
 */
package com.tongxin.speech;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.content.Context;
import android.os.Environment;
import com.example.update.client;

public class readFile {
	String b=client.banben;

	private Context context;
	/** SD卡是否存在**/
	private boolean hasSD = false;
	/** SD卡的路径**/
	private String SDPATH;
	/** 当前程序包的路径**/
	private String FILESPATH;
	private int num;
	public readFile(Context context)
	{//构造函数
		this.context = context;
		hasSD = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
		SDPATH = Environment.getExternalStorageDirectory().getPath();
		FILESPATH = this.context.getFilesDir().getPath();
		num=0;
	}


	/**
	 * 读取SD卡中文本文件
	 *
	 * @param fileName
	 * @return
	 */
	public int getFileNum(String fileName){
		StringBuffer sb = new StringBuffer();
		File file = new File(SDPATH + "//"+b+"//" + fileName);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			tempString = reader.readLine();
			num=Integer.parseInt(tempString);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return num;
	}
	public String[] readSDFile(String fileName,int n) {
		StringBuffer sb = new StringBuffer();
		File file = new File(SDPATH + "//"+b+"//" + fileName);
		String[] des=new String[n];
		int i=0;
		BufferedReader reader = null;
		try {
			System.out.println("以行为单位读取文件内容，一次读一整行：");
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			tempString = reader.readLine();

			while ((tempString = reader.readLine()) != null) {
				des[i]=tempString;
				i++;
			}
			reader.close();
			for(i=0;i<n;i++){
				System.out.println("line"+(i+1)+"  "+des[i]);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return des;
	}

	public String readAllFile(String filename)
	{
		File file1 = new File(SDPATH + "//"+b+"//" + filename);
		BufferedReader reader1 = null;
		String ss="";
		try {
			reader1 = new BufferedReader(new FileReader(file1));
			String tempString ="";
			while ((tempString = reader1.readLine()) != null) {
				ss+=tempString;//整个文件做一个字符串
			}
			reader1.close();
			// System.out.println(ss);


		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader1 != null) {
				try {
					reader1.close();
				} catch (IOException e1) {
				}
			}
		}
		return ss;
	}

	public String getFILESPATH() {
		return FILESPATH;
	}

	public String getSDPATH() {
		return SDPATH;
	}

	public boolean hasSD() {
		return hasSD;
	}


} 