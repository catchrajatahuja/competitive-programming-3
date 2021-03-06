package chap2.ds.custom.tree.p12532;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeMap;

import javax.swing.text.Segment;

public class Main {
	/**
	 * @param args
	 */
	static InputReader in 		= new InputReader(System.in);
    //static OutputWriter out	=	new OutputWriter(System.out);
	static BufferedReader br=null;

	public static void main(String[] args) throws IOException {
		br=new BufferedReader(new InputStreamReader(System.in));
		while(true) {
			try {
				if(process()==0) {
					break;
				}
			} catch (Exception e) {
				return;
			}
            
            
		}
		//out.flush();
		//out.close();
	}
	
	static String readLine(){
		try {
			return br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	static int[] giveArray(String[] str) {
		int[] ret = new int[str.length];
		for(int i = 0; i < str.length; i++) {
			if(str[i].compareTo("") != 0)
				ret[i] = Integer.parseInt(str[i].trim());
		}
		return ret;
	}
	static List<Integer> giveArrayList(String[] str) {
		List<Integer> ret = new ArrayList<>();
		for(int i = 0; i < str.length; i++) {
			if(str[i].compareTo("") != 0)
				ret.add(Integer.parseInt(str[i].trim()));
		}
		return ret;
	}
	static int findClosest (List<Integer> ll, int val, boolean comp) {
		int a = 0, b = ll.size() - 1;
		
		if(ll.size() == 0)
			return -1;
		if(comp && ll.get(0) > val) {
			return -1;
		}
		if(!comp && ll.get(ll.size()-1) < val) {
			return -1;
		}
		while(a != b) {
			int mid = a + b;
			mid /= 2;
			if((b - a) == 1) {
				if(comp) {
					if(ll.get(b) <= val) {
						a = b;
					}
					else {
						b = a;
					}
				}
				else {
					if(ll.get(a) >= val) {
						b = a;
					}
					else {
						a = b;
					}
				}
				break;
			}
			
			boolean v1 = ll.get(mid) < val;
			boolean v2 = ll.get(mid) > val;
			boolean v3 = ll.get(mid) == val;
			if(v3) {
				a = b = mid;
				break;
			}
			if(comp) {
				if(v1) {
					a = mid;
				}
				else {
					b = mid;
				}
			}
			else {
				if(v2) {
					b = mid;
				}
				else {
					a = mid;
				}
			}
		}
		return a;
	}
	static List<String> ans = new ArrayList<>();
	static int casen = 1;
	static int process() throws Exception{
		int n = in.readInt();
		int m = in.readInt();
		if(n == 0)
			return 0;
		int[] arr = new int[n];
		for(int i = 0; i < n; i++) {
			arr[i] = in.readInt();
		}
		FenwickTree ft = new FenwickTree(arr);
		StringBuffer sb = new StringBuffer();
		//System.out.println("arr  : "+Arrays.toString(arr));

		while(m-- > 0) {
			String cmd = in.readString();
			char c = cmd.trim().charAt(0);
			int a = in.readInt();
			int b = in.readInt();
			switch (c) {
			case 'C':
				ft.adjust(a, b, arr[a-1]);
				arr[a-1] = b;
				break;
			case 'P':
				int res = ft.rsq(a, b);
				char ch = '0';
				if(res > 0)
					ch = '+';
				if(res < 0)
					ch = '-';
				sb.append(ch);
				break;
			default:
				break;
			}
		}
		System.out.println(sb);
		
		return 1;
	}
	
	static class FenwickTree {
		
		private int LSOne(int S) { return (S & (-S)); }
		int[] arr = null;
		int[] zeros = null;
		int n;
		
		public FenwickTree(int[] arr) {
			n = arr.length;
			this.arr = new int[n + 1];
			this.zeros = new int[n + 1];
			Arrays.fill(this.arr, 1);

			for(int i = 0; i < arr.length; i++) {
				adjust(i+1, arr[i]);
			}
		}
		void adjust(int k, int v) {
			//System.out.println("adjust : "+k+" ::: "+Integer.toBinaryString(k)+" : "+v);
			for (; k <= n; k += LSOne(k))  {
				if(v == 0)
					zeros[k]++;
				arr[k] *= (v >= 0 ? 1 : -1);
			}
		}
		void adjust(int k, int v, int old) {
			//System.out.println("adjust : "+k+" ::: "+Integer.toBinaryString(k)+" : "+v);
			for (; k <= n; k += LSOne(k))  {
				if(v == 0)
					zeros[k]++;
				if(old == 0)
					zeros[k]--;
				arr[k] *= (v >= 0 ? 1 : -1);
				arr[k] *= (old >= 0 ? 1 : -1);
			}
		}
		public int rsq1(int b) {
			int prod = 1;
			for(;b > 0; b-= LSOne(b)) {
				prod *= arr[b];
			}
			
			return prod;
		}
		public int rsq2(int b) {
			int ret = 0;
			for(;b > 0; b-= LSOne(b)) {
				ret += zeros[b];
			}
			
			return ret;
		}
		public int rsq(int a, int b) {
			int z = rsq2(b) - (a == 1 ? 0 : rsq2(a - 1));
			if(z == 0)
				return rsq1(b) * (a == 1 ? 1 : rsq1(a - 1));
			else return 0;
		}
	}
	
}
class InputReader {

	private InputStream stream;
	private byte[] buf = new byte[1024];
	private int curChar;
	private int numChars;
	private SpaceCharFilter filter;

	public InputReader(InputStream stream) {
		this.stream = stream;
	}

	public int read() {
		if (numChars == -1)
			throw new InputMismatchException();
		if (curChar >= numChars) {
			curChar = 0;
			try {
				numChars = stream.read(buf);
			} catch (IOException e) {
				throw new InputMismatchException();
			}
			if (numChars <= 0)
				return -1;
		}
		return buf[curChar++];
	}

	public int readInt() {
		int c = read();
		while (isSpaceChar(c))
			c = read();
		int sgn = 1;
		if (c == '-') {
			sgn = -1;
			c = read();
		}
		int res = 0;
		do {
			if (c < '0' || c > '9')
				throw new InputMismatchException();
			res *= 10;
			res += c - '0';
			c = read();
		} while (!isSpaceChar(c));
		return res * sgn;
	}

	public String readString() {
		int c = read();
		while (isSpaceChar(c))
			c = read();
		StringBuilder res = new StringBuilder();
		do {
			res.appendCodePoint(c);
			c = read();
		} while (!isSpaceChar(c));
		return res.toString();
	}

	public boolean isSpaceChar(int c) {
		if (filter != null)
			return filter.isSpaceChar(c);
		return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
	}

	public String next() {
		return readString();
	}

	public interface SpaceCharFilter {
		public boolean isSpaceChar(int ch);
	}
}

class OutputWriter {
	private final PrintWriter writer;

	public OutputWriter(OutputStream outputStream) {
		writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
	}

	public OutputWriter(Writer writer) {
		this.writer = new PrintWriter(writer);
	}

	public void print(Object...objects) {
		for (int i = 0; i < objects.length; i++) {
			if (i != 0)
				writer.print(' ');
			writer.print(objects[i]);
		}
	}

	public void printLine(Object...objects) {
		print(objects);
		writer.println();
	}

	public void close() {
		writer.close();
	}

	public void flush() {
		writer.flush();
	}

	}

class IOUtils {

	public static int[] readIntArray(InputReader in, int size) {
		int[] array = new int[size];
		for (int i = 0; i < size; i++)
			array[i] = in.readInt();
		return array;
	}

}
