package model;

public class Table {
<<<<<<< HEAD
=======

	int len=0;
	
	String mainTxt = "";
	String sentSpeaker[]=new String[50];
	String resultSent[] = new String[50];
	String tempString[] = new String[3];

	String resultVoice[] = new String[50];
	String resultName[]=new String[50];
	String resultGender[]=new String[50];
	String resultAge[]=new String[50];
	String resultEmo1[] = new String[50]; //감정 종류
	String resultEmo2[] = new String[50]; //세기
	
>>>>>>> feature/server
	String voiceOp[] = {"--------voice-------", "ema&amp;nea/여/82년생", "emb&amp;neb/여/84년생", "emc&amp;nec/여/74년생",
                        "emd&amp;ned/여/91년생", "eme&amp;nee/여/86년생", "emf&amp;nek/남/82년생",
                        "emg&amp;nel/남/85년생", "emh&amp;nem/남/81년생", "emi&amp;nen/남/83년생",
                        "emj&amp;neo/남/87년생", "nef/여/85년생", "neg/여/84년생", "neh/여/87년생",
                        "nei/여/87년생", "nej/여/84년생", "nep/남/89년생", "neq/남/86년생", "ner/남/85년생",
                        "nes/남/87년생", "net/남/84년생"};

	String voiceVal[] = {"ema&nea", "ema&nea", "emb&neb", "emc&nec", "emd&ned", "eme&nee", "emf&nek", "emg&nel", "emh&nem", 
                       "emi&nen", "emj&neo", "nef", "neg", "neh", "nei", "nej", "nep", "neq", "ner", "nes", "net"};
	
	String emoOp[] = {"----emotion----", "기쁨", "화남","슬픔", "중립"};
	String emoVal[] = {"neutral", "joy", "angry", "sad", "neutral"};
	
	public String getVoiceOp(int i) {
		return voiceOp[i];
	}
	public String getVoiceVal(int i) {
		return voiceVal[i];
	}
	public String getEmoOp(int i) {
		return emoOp[i];
	}
	public String getEmoVal(int i) {
		return emoVal[i];
	}
<<<<<<< HEAD
=======
	//voice 값 저장
	public void setVoiceEmo(String[] v,String[] e1,String[] e2) {
		for(int i=0;i<len;i++) {
			if(v[i].contains("/")) {
				tempString=v[i].split("/");
				resultName[i]=tempString[0];
				resultGender[i]=tempString[1];
				resultAge[i]=tempString[2];
			}else {
				resultName[i]=v[i];
				resultGender[i]="";
				resultAge[i]="";
			}
		}
		resultEmo1=e1;
		resultEmo2=e2;
	}
	
	public String getVoiceName(int i) {
		return resultName[i];
	}
	public String getEmo1(int i) {
		return resultEmo1[i];
	}
	public String getEmo2(int i) {
		return resultEmo2[i];
	}

>>>>>>> feature/server
}