import java.util.Arrays;

public class BigNum {

	// tabInt contains all de decimal that represent the number :
	// Example :
	// 		For the number 12345
	// 		tabInt = { 5 , 4 , 3 , 2 , 1 }
	private int [] tabInt;

	// Default Constructor
	public BigNum() {
		this.tabInt = new int[1];
	}

	// Constructor that represent the given integer.
	public BigNum(int num) {
		if(num < 0) {
			System.out.println("Warning : the integer has to be positive ! We take the absolute value");
			num = -1*num;
		}
		String sNum = num + "";
		this.tabInt = new int[sNum.length()];
		for(int i = 0 ; i < this.tabInt.length ; i++) {
			this.tabInt[this.tabInt.length - 1 - i]= Integer.parseInt(""+sNum.charAt(i));
		}
	}

	// Constructor that initialize this.tabInt by the given integer array.
	public BigNum(int [] newTabInt) {
		this.tabInt = newTabInt;
	}

	// Getter
	public int [] getTabInt() {
		return this.tabInt;
	}


	public String toString() {
		String num = "";
		for(int i = this.tabInt.length - 1 ; i >= 0 ; i--) {
			num += tabInt[i];
		}
		return num;
	}

	// Function that returns the addition between this and b. (return a new BigNum)
	public BigNum add(BigNum b) {
		int retenu = 0;
		int [] minLengthTab;
		int [] maxLengthTab;
		int [] newTabInt;
		if(this.tabInt.length > b.getTabInt().length){
			maxLengthTab = this.tabInt;
			minLengthTab = b.getTabInt();
		}
		else {
			maxLengthTab = b.getTabInt();
			minLengthTab = this.tabInt;
		}

		newTabInt = new int[maxLengthTab.length + 1];
		for(int i = 0 ; i < newTabInt.length - 1 ; i++) {
			retenu = 0;
			newTabInt[i] += maxLengthTab[i];
			if(i < minLengthTab.length) {
				newTabInt[i] += minLengthTab[i];
			}
			if(newTabInt[i]>=10) {
				newTabInt[i] = newTabInt[i]%10;
				newTabInt[i+1]+=1;
				if(i==newTabInt.length-2) {
					retenu = 1;
				}
			}
		}
		if(retenu == 1) {
			return new BigNum(newTabInt);
		}
		else {
			return new BigNum(Arrays.copyOfRange(newTabInt, 0, newTabInt.length-1));
		}
	}

	// Function that returns the absolute value of this - b. (return a new BigNum)
	public BigNum subtract(BigNum b) {
		int [] minLengthTab;
		int [] maxLengthTab;
		int [] newTabInt;
		if(this.tabInt.length >= b.getTabInt().length){
			maxLengthTab = this.tabInt;
			minLengthTab = b.getTabInt();
		}
		else {
			maxLengthTab = b.getTabInt();
			minLengthTab = this.tabInt;
		}

		boolean negativeNumber = false;
		newTabInt = new int[maxLengthTab.length];
		for(int i = 0 ; i < newTabInt.length ; i++) {
			newTabInt[i] += maxLengthTab[i];
			if(i < minLengthTab.length) {
				newTabInt[i] -= minLengthTab[i];
			}
			if(newTabInt[i] < 0) {
				newTabInt[i] = 10 + newTabInt[i];

				if(i < newTabInt.length -1) {
					newTabInt[i+1] -=1;
				}
				else {
					negativeNumber = true;
				}
			}
		}
		if(negativeNumber) {
			for(int i = 0 ; i < newTabInt.length ; i++) {
				if(10 - newTabInt[i]!= 10) {
					newTabInt[i] = 10-newTabInt[i];
					if(i!=newTabInt.length -1) {
						newTabInt[i+1]=(newTabInt[i+1]+1)%10;
					}
				}
			}
		}

		return new BigNum(removeUselessZeros(newTabInt));
	}

	public int [] removeUselessZeros(int [] tab) {
		for(int i = tab.length - 1 ; i >=0 ; i --) {
			if(tab[i]!=0) {
				return Arrays.copyOfRange(tab, 0, i+1);
			}
		}
		return new int [1];
	}

	public boolean equals(BigNum b) {
		if(this.tabInt.length!=b.getTabInt().length) {
			return false;
		}
		for(int i = 0 ; i < this.tabInt.length ; i++) {
			if(tabInt[i]!= b.getTabInt()[i]) {
				return false;
			}
		}
		return true;
	}

	public boolean isBigger(BigNum b) {
		if(this.tabInt.length>b.getTabInt().length) {
			return true;
		}
		else if(this.tabInt.length<b.getTabInt().length) {
			return false;
		}
		else {
			for(int i = this.tabInt.length -1 ; i >= 0 ; i--) {
				if(this.tabInt[i] > b.getTabInt()[i]) {
					return true;
				}
				else if(this.tabInt[i] < b.getTabInt()[i]) {
					return false;
				}
			}
		}
		return false;
	}

	public BigNum multiply(BigNum b) {
		int [] newTabInt = new int [this.tabInt.length + b.getTabInt().length];
		for(int i = 0 ; i < b.getTabInt().length ; i++) {
			for(int j = 0 ; j < this.tabInt.length ; j++) {
				newTabInt[j+i] += b.getTabInt()[i] * this.tabInt[j] ;
			}
		}

		for(int i = 0 ; i < newTabInt.length ; i++) {
			if(newTabInt[i] >= 10) {
				int retenu = newTabInt[i]/10 - ((newTabInt[i]/10)%1);
				newTabInt[i]= newTabInt[i]%10;
				newTabInt[i+1] += retenu;
			}
		}
		return new BigNum(removeUselessZeros(newTabInt));
	}

	public BigNum div(BigNum b) {
		if(b.equals(new BigNum(0))) {
			System.out.println("We can't divide by 0!!");
			return null;
		}
		int [] newTabInt = new int [this.tabInt.length];
		int [] copyTabInt = new int[this.tabInt.length];
		System.arraycopy( this.tabInt, 0, copyTabInt, 0, this.tabInt.length );

		int i = this.tabInt.length-1;


		BigNum temp = new BigNum(0);
		int rank = this.tabInt.length-1;
		while(b.isBigger(temp)) {
			if(rank<0) {
				return new BigNum(0);
			}
			temp = new BigNum(Arrays.copyOfRange(copyTabInt, rank , copyTabInt.length));
			rank--;
		}

		while(true){
			for(int j = 0 ; j <= 10 ; j++) {
				BigNum n = b.multiply(new BigNum(j));
				if(n.equals(temp)) {
					newTabInt[i]=j;
					break;
				}
				if(n.isBigger(temp)) {
					newTabInt[i]=j-1;
					break;
				}
			}
			if(rank<0) {
				break;
			}
			temp = temp.subtract(b.multiply(new BigNum(newTabInt[i]))).multiply(new BigNum(10)).add(new BigNum(this.tabInt[rank]));
			rank--;
			i--;
		}


		return new BigNum(Arrays.copyOfRange(newTabInt, i, newTabInt.length));
	}

	public static BigNum fact(int n) {
		BigNum fact = new BigNum(1);
		for(int i = 2 ; i <= n ; i++) {
			fact = fact.multiply(new BigNum(i));
		}
		return fact;
	}

	public static BigNum fibo(int n) {
		if(n==0) {
			return new BigNum();
		}
		int F_prec = 0;
		int F_suiv = 1;
		int F_save = 0;
		for(int i = 1 ; i < n ; i++) {
			F_save = F_prec;
			F_prec = F_suiv;
			F_suiv =  F_save + F_prec;
		}
		return new BigNum(F_suiv);
	}

	public static BigNum pow(BigNum b, int n) {
		BigNum result = new BigNum(1);
		for(int i = 0 ; i < n ; i++) {
			result = result.multiply(b);
		}
		return result;
	}

	public static void verifFibo(int n) {
		System.out.println("Verification of Fibonacci");
		BigNum [] fibonacciTerms = new BigNum [n];
		for(int i = 0 ; i < n ; i++) {
			System.out.println();
			fibonacciTerms[i] = BigNum.fibo(i);
			System.out.print(fibonacciTerms[i]);
			if(i>2) {
				BigNum sumFibo = new BigNum(fibonacciTerms[i].getTabInt()).subtract(fibonacciTerms[i-1]).subtract(fibonacciTerms[i-2]);
				if(sumFibo.equals(new BigNum(0))) {
					System.out.print("  -  Sum equals 0 !");
				}
				else{
					System.out.print(" -  Error ! ");
				}
			}
		}
		System.out.println();
	}

	public static void Newton(int n) {
		System.out.println("Verification of Newton");
		BigNum [] k_nTerms = new BigNum [n+1];
		for(int k = 0 ; k <= n ; k++) {
			k_nTerms[k] = BigNum.fact(n).div(BigNum.fact(k).multiply(BigNum.fact(n-k)));
			System.out.println(k_nTerms[k]);
		}
		BigNum sum = new BigNum(0);
		for(int i = 0 ; i <= n ; i++) {
			sum = sum.add(k_nTerms[i]);
		}

		System.out.println("Result : " + sum.equals(BigNum.pow(new BigNum(2), n)));
	}

	public static void main (String [] args) {
		BigNum n1 = new BigNum(6227020);
		BigNum n2 = new BigNum(6096384);
		System.out.println("number 1 : " + n1);
		System.out.println("number 2 : " + n2);
		System.out.println("Sum : "+ n1.add(n2));
		System.out.println("Subtract : "+ n1.subtract(n2));
		System.out.println("Equals : "+n1.equals(n2));
		System.out.println("isBigger : "+n1.isBigger(n2));
		System.out.println("Multiply : " + n1.multiply(n2));
		System.out.println("Divide " + n1.div(n2));
		System.out.println("Fact : " + BigNum.fact(10));
		System.out.println("Fibonacci : " + BigNum.fibo(15));
		System.out.println("Pow : " + BigNum.pow(n1, 0));
		BigNum.verifFibo(10);
		BigNum.Newton(20);
	}
}
