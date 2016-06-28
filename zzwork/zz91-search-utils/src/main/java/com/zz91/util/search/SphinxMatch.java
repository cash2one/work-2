/*
 * $Id$
 */

package com.zz91.util.search;

import java.util.ArrayList;

/**
 * Matched document information, as in search result.
 */
public class SphinxMatch
{
	/** Matched document ID. */
	public long		docId;

	/** Matched document weight. */
	public int			weight;

	/** Matched document attribute values. */
	@SuppressWarnings("rawtypes")
	public ArrayList	attrValues;


	/** Trivial constructor. */
	@SuppressWarnings("rawtypes")
	public SphinxMatch ( long docId, int weight )
	{
		this.docId = docId;
		this.weight = weight;
		this.attrValues = new ArrayList();
	}
}

/*
 * $Id$
 */
