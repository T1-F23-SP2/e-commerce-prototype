package com.example.ecommerceprototype.pim.util;

import java.util.ArrayList;

public class SafeRemoveArrayList<E> extends ArrayList<E> {

	private final ArrayList<E> toRemove = new ArrayList<>();

	public void safeRemoveAdd(E e) {
		this.toRemove.add(e);
	}

	public void safeRemoveSubmit() {
		this.toRemove.forEach(this::remove);
		this.toRemove.clear();
	}
}
