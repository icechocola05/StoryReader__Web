package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class loadText {
	private int line=0;
	public String mainTxt="";
	public Table FileRead(File file,Table t) {
		Table table=t;
		try {
			FileInputStream ins = new FileInputStream(file);
			BufferedReader reader = new BufferedReader(new InputStreamReader(ins, "UTF-8"));
			while(true) {
				String str = reader.readLine();
				if (str == null) break;
				mainTxt+=str+"\n";
				t.setLength(++line);
			}
			t.setSent(mainTxt);
			t.setSpeaker();
			
			reader.close();
			ins.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return table;
	}
}