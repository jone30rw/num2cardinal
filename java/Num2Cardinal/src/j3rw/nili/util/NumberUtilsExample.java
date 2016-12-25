package j3rw.nili.util;

public class NumberUtilsExample {
	
	public static void main(String[] args) {
		System.out.println(
			NumberUtils.numberToCardinal(-2_001_001));
		// output => منفی دو میلیون  و یک هزار  و یک 
		
		System.out.println(
				NumberUtils.numberToCardinal(-4_000.16_546, 5));
		// output => منفی چهار هزار  اعشاری شانزده هزار  و پنج صد  و چهل  و شش  صد هزارم
		
		System.out.println(
				NumberUtils.numberToCardinal(0.16_546, 5));
		// output => صفر اعشاری شانزده هزار  و پنج صد  و چهل  و شش  صد هزارم
	}
}