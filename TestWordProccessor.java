
public class TestWordProccessor {

	public TestWordProccessor() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//True test cases
		System.out.println(WordProcessor.isAdjacent("nick", "kick"));
		System.out.println(WordProcessor.isAdjacent("abs", "ans"));
		System.out.println(WordProcessor.isAdjacent("pen", "pan"));
		System.out.println(WordProcessor.isAdjacent("pan", "pad"));
		System.out.println(WordProcessor.isAdjacent("ant", "at"));
		System.out.println(WordProcessor.isAdjacent("ante", "ate"));
		System.out.println(WordProcessor.isAdjacent("be", "bed"));
		System.out.println(WordProcessor.isAdjacent("bee", "be"));
		System.out.println(WordProcessor.isAdjacent("abe", "be"));
		
		
		System.out.println("-------------------------------");
		
		
		//False test cases
		System.out.println(WordProcessor.isAdjacent("bad", "dab"));
		System.out.println(WordProcessor.isAdjacent("badee", "bad"));
		System.out.println(WordProcessor.isAdjacent("badx", "hady"));
		System.out.println(WordProcessor.isAdjacent("zoology", "biology"));
		
		System.out.println(WordProcessor.isAdjacent("zooo", "broo"));

}
}
