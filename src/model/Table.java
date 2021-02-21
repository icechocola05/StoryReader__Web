package model;

public class Table {
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
}