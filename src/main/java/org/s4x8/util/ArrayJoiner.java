
package org.s4x8.util;

public class ArrayJoiner {
	public static String join(Object[] elems, CharSequence joiner) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < elems.length; i++) {
			if (i != 0) {
				sb.append(joiner);
			};
			if (elems[i] instanceof CharSequence) {
				sb.append(elems[i]);
			} else {
				sb.append(elems[i].toString());
			};
		};
		return sb.toString();
	};

	public static String join(Object[] elems) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < elems.length; i++) {
			if (elems[i] instanceof CharSequence) {
				sb.append(elems[i]);
			} else {
				sb.append(elems[i].toString());
			};
		};
		return sb.toString();
	};
};
