package com.tongxin.map;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import android.content.Context;
public class WriteToSD {
	 private Context context;
	 String filePath = android.os.Environment.getExternalStorageDirectory()+"/map";
	 public WriteToSD(Context context){
	 this.context = context;
	 if(!isExist()){
	  write();
	 }
	 }
	 private void write(){
	 InputStream inputStream;
	 InputStream inputStream2;
	 try {
	  inputStream = context.getResources().getAssets().open("bupt.txt");
	  inputStream2=context.getResources().getAssets().open("node.txt");
	  File file = new File(filePath);
	  if(!file.exists()){
	  file.mkdirs();
	  }
	  FileOutputStream fileOutputStream = new FileOutputStream(filePath + "/bupt.txt");
	  FileOutputStream fileOutputStream2 = new FileOutputStream(filePath + "/node.txt");
	  byte[] buffer = new byte[512];
	  int count = 0;
	  while((count = inputStream.read(buffer)) > 0){
	  fileOutputStream.write(buffer, 0 ,count);
	  }
	  fileOutputStream.flush();
	  fileOutputStream.close();
	  inputStream.close();
	  byte[] buffer2 = new byte[512];
	  int count2 = 0;
	  while((count2 = inputStream2.read(buffer2)) > 0){
	  fileOutputStream2.write(buffer2, 0 ,count2);
	  }
	  fileOutputStream2.flush();
	  fileOutputStream2.close();
	  inputStream2.close();
	  System.out.println("success");
	 } catch (IOException e) {
	  e.printStackTrace();
	 }
	 }
	 private boolean isExist(){
	 File file = new File(filePath + "/bupt.txt");
	 File file2 = new File(filePath + "/node.txt");
	 if(file.exists()&&file2.exists()){
	  return true;
	 }else{
	  return false;
	 }
	 }

}
