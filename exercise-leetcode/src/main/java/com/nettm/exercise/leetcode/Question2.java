package com.nettm.exercise.leetcode;

public class Question2 {

	public static void main(String[] args) {
		ListNode l1 = new ListNode(2);
		l1.next = new ListNode(4);
		l1.next.next = new ListNode(3);

		ListNode l2 = new ListNode(5);
		l2.next = new ListNode(6);
		l2.next.next = new ListNode(4);

		ListNode result = Question2.addTwoNumbers(l1, l2);
		System.out.println(result.val + ":" + result.next.val + ":" + result.next.next.val);
	}

	public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
		ListNode result = new ListNode(0);
		boolean carry = false;
		while (l1 != null || l2 != null || carry) {
			int tmp = 0;
			if (carry) {
				tmp = 1;
				carry = false;
			}

			if (l1 != null) {
				tmp = l1.val;
			}

			if (l2 != null) {
				tmp = l1.val + l2.val;
				if (tmp >= 10) {
					carry = true;
					tmp -= 10;
				}
			}

			l1 = l1.next;
			l2 = l2.next;
			result.val = tmp;
			result.next = new ListNode(0);
		}

		return result;
	}

}
