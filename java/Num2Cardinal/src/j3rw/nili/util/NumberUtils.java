package j3rw.nili.util;

public class NumberUtils {

	public static String numberToCardinal(double number, int decimalPercision) {
		// some constant variables used to store 
		// string literal for numbers
		final String   DEC_SP = "اعشاری ";
		final String[] _10th_TO_100Bth = 
			{ " ", "دهم ", "صدم ", 
			  "هزارم ", "ده هزارم ", "صد هزارم ", 
			  "میلیونم ", "ده میلیونم ", "صد میلیونم ", 
			  "میلیاردم ", "ده میلیاردم ", "صد میلیاردم " };
		
		// first convert the real part
		long realPart = (long) number;
		String cardinal = realNumberToCardinal(realPart);

		// only if we are said so
		if (decimalPercision != 0) {
			// remove the real part
			number %= 1;
			// check because can not do more than 11 decimal precision
			decimalPercision = Math.min(11, decimalPercision);
			// then convert the decimal part to a real number
			long decimalPart = Math.round(number * Math.pow(10, decimalPercision));
			
			decimalPart = Math.abs(decimalPart);
	
			// don't convert if it is zero 
			if (decimalPart != 0)
				cardinal += 
					DEC_SP + 
					realNumberToCardinal(decimalPart) + " " + 
					_10th_TO_100Bth[decimalPercision];
		}

		return cardinal.trim();
	}

	public static String realNumberToCardinal(long number) {
		// some constant variables used to store 
		// string literal for numbers
		final String   SP = "و ";
		final String   NEG = "منفی ";
		final String   _0 = "صفر ";
		final String[] _1_TO_19 = 
			{ " ", "یک ", "دو ", "سه ", "چهار ", 
			  "پنج ", "شش ", "هفت ", "هشت ", "نه ", 
 			  "ده ", "یازده ", "دوازده ", "سیزده ", "چهارده ", "پانزده ", 
 			  "شانزده ", "هفده ", "هژده ", "نوزده " };
		final String[] _10_TO_100 = 
			{ " ", "ده ", "بیست ", "سی ", "چهل ", 
			  "پنجاه ", "شصت ", "هفتاد ", "هشتاد ", "نود " };
		final String[] _100_TO_1B = 
			{ "صد ", "هزار ", "میلیون ", "میلیارد " };

		// current solution process the number 
		// in groups of three digit, 
		// this variable keeps the group number
		int numGroupIdx = 0;
		
		// our final result store here :)
		String cardinal = "";

		String sign = "";
		// prepend the negative word
		if (number < 0) {
			sign = NEG;
			
			// and make it positive
			number = Math.abs(number);
		}
		
		if (number == 0)
			cardinal = _0;
		else {
			// we have a problem with و word which
			// joins other word, we have special case
			// like 2_000_000 which we don't want to be 
			// like دو میلیون و. 
			// notice the و shouldn't be there.
			boolean hasLeadingDigit = false;

			// go on until there is nothing left
			while (number != 0) {
				// ok, find the current iteration 3 digit number
				int subNumber = (int) (number % 1000);
				// backup it because we need it in the last
				// statements in this loop
				int subNumberBak = subNumber;
				// and get rid of it
				number /= 1000;

				// store current iteration result
				String subCardinal = "";

				// we don't to do any thing if it is zero
				if (subNumber != 0) {
					
					// but if it bigger than 99
					if (subNumber > 99) {
						// avoid یک for hundreds
						if (subNumber / 100 != 1)
							subCardinal += _1_TO_19[subNumber / 100];

						// append صد at the end
						subCardinal += _100_TO_1B[0];
						// remove the hundreds
						subNumber %= 100;

						// if we have still any thing left 
						// then add a و at the end
						if (subNumber != 0)
							subCardinal += SP;
					}

					// we already processed the hundreds
					// now lets tackles what is left
					
					// under 19 is easy cake
					if (subNumber <= 19)
						subCardinal += _1_TO_19[subNumber];
					
					// bigger than 19 and smaller than 100
					else {
						// process the tens
						subCardinal += _10_TO_100[subNumber / 10];
						// remove the tens
						subNumber %= 10;

						// if any thing left then add a و
						if (subNumber != 0)
							subCardinal += SP;

						// process the ones
						subCardinal += _1_TO_19[subNumber % 10];
					}

					// add group append 
					// not for group zero, because it doesn't need one
					if (numGroupIdx != 0) {
						subCardinal += _100_TO_1B[numGroupIdx];

						// append a و only if there any leading digit
						if (hasLeadingDigit)
							subCardinal += SP;
					}

					// append current iteration result
					cardinal = subCardinal + cardinal;
				}

				// as long as we are processing the number from 
				// left to right (from ones to thousands), then
				// i use hasLeadingDigit to check if we already
				// have any digit other than zero on the right 
				// side.
				// and subNumberBak is used here.
				// why not put this line at the top 
				// and not using subNumberBak, 
				// believe me these lines should be here
				if (subNumberBak != 0)
					hasLeadingDigit = true;

				// THIS LINE DOESN'T NEED COMMENT :)
				numGroupIdx++;
			}
		}
		
		// prepend sign
		cardinal = sign + cardinal;

		// AND FINALLY WE GOT OUR RESULT
		return cardinal.trim();
	}
}