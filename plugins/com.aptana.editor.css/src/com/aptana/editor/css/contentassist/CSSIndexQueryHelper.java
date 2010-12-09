/**
 * This file Copyright (c) 2005-2010 Aptana, Inc. This program is
 * dual-licensed under both the Aptana Public License and the GNU General
 * Public license. You may elect to use one or the other of these licenses.
 * 
 * This program is distributed in the hope that it will be useful, but
 * AS-IS and WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE, TITLE, or
 * NONINFRINGEMENT. Redistribution, except as permitted by whichever of
 * the GPL or APL you select, is prohibited.
 *
 * 1. For the GPL license (GPL), you can redistribute and/or modify this
 * program under the terms of the GNU General Public License,
 * Version 3, as published by the Free Software Foundation.  You should
 * have received a copy of the GNU General Public License, Version 3 along
 * with this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 * 
 * Aptana provides a special exception to allow redistribution of this file
 * with certain other free and open source software ("FOSS") code and certain additional terms
 * pursuant to Section 7 of the GPL. You may view the exception and these
 * terms on the web at http://www.aptana.com/legal/gpl/.
 * 
 * 2. For the Aptana Public License (APL), this program and the
 * accompanying materials are made available under the terms of the APL
 * v1.0 which accompanies this distribution, and is available at
 * http://www.aptana.com/legal/apl/.
 * 
 * You may view the GPL, Aptana's exception and additional terms, and the
 * APL in the file titled license.html at the root of the corresponding
 * plugin containing this source file.
 * 
 * Any modifications to this file must keep this entire header intact.
 */
package com.aptana.editor.css.contentassist;

import java.io.IOException;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.aptana.editor.css.CSSPlugin;
import com.aptana.editor.css.contentassist.index.CSSIndexConstants;
import com.aptana.editor.css.contentassist.index.CSSIndexReader;
import com.aptana.editor.css.contentassist.model.ElementElement;
import com.aptana.editor.css.contentassist.model.PropertyElement;
import com.aptana.editor.css.contentassist.model.PseudoClassElement;
import com.aptana.editor.css.contentassist.model.PseudoElementElement;
import com.aptana.index.core.Index;
import com.aptana.index.core.IndexManager;

public class CSSIndexQueryHelper
{
	/**
	 * getIndex
	 * 
	 * @return
	 */
	public static Index getIndex()
	{
		return IndexManager.getInstance().getIndex(URI.create(CSSIndexConstants.METADATA_INDEX_LOCATION));
	}

	private CSSIndexReader _reader;

	/**
	 * CSSIndexQueryHelper
	 */
	public CSSIndexQueryHelper()
	{
		this._reader = new CSSIndexReader();
	}

	/**
	 * getClasses
	 * 
	 * @return
	 */
	public Map<String, String> getClasses(Index index)
	{
		return this._reader.getValues(index, CSSIndexConstants.CLASS);
	}

	/**
	 * getColors - Returns the unique set of colors used within the project.
	 * 
	 * @param index
	 * @return
	 */
	public Set<String> getColors(Index index)
	{
		Set<String> result = Collections.emptySet();

		if (index != null)
		{
			Map<String, String> colorMap = this._reader.getValues(index, CSSIndexConstants.COLOR);

			if (colorMap != null)
			{
				result = colorMap.keySet();
			}
		}

		return result;
	}

	/**
	 * getElements
	 * 
	 * @return
	 */
	public List<ElementElement> getElements()
	{
		List<ElementElement> result = Collections.emptyList();

		try
		{
			result = this._reader.getElements(getIndex());
		}
		catch (IOException e)
		{
			CSSPlugin.logError(e.getMessage(), e);
		}

		return result;
	}

	/**
	 * getIDs
	 * 
	 * @param index
	 * @return
	 */
	public Map<String, String> getIDs(Index index)
	{
		return this._reader.getValues(index, CSSIndexConstants.IDENTIFIER);
	}

	/**
	 * getProperties
	 * 
	 * @return
	 */
	public List<PropertyElement> getProperties()
	{
		List<PropertyElement> result = Collections.emptyList();

		try
		{
			result = this._reader.getProperties(getIndex());
		}
		catch (IOException e)
		{
			CSSPlugin.logError(e.getMessage(), e);
		}

		return result;
	}

	/**
	 * getProperty
	 * 
	 * @return
	 */
	public PropertyElement getProperty(String name)
	{
		PropertyElement result = null;

		try
		{
			List<PropertyElement> properties = this._reader.getProperties(getIndex(), name);

			if (properties != null)
			{
				for (PropertyElement property : properties)
				{
					if (name.equals(property.getName()))
					{
						result = property;
						break;
					}
				}
			}
		}
		catch (IOException e)
		{
			CSSPlugin.logError(e.getMessage(), e);
		}

		return result;
	}

	/**
	 * getPseudoClasses
	 * 
	 * @return
	 */
	public List<PseudoClassElement> getPseudoClasses()
	{
		List<PseudoClassElement> result = Collections.emptyList();

		try
		{
			result = this._reader.getPseudoClasses(getIndex());
		}
		catch (IOException e)
		{
			CSSPlugin.logError(e.getMessage(), e);
		}

		return result;
	}

	/**
	 * getPseudoElements
	 * 
	 * @return
	 */
	public List<PseudoElementElement> getPseudoElements()
	{
		List<PseudoElementElement> result = Collections.emptyList();

		try
		{
			result = this._reader.getPseudoElements(getIndex());
		}
		catch (IOException e)
		{
			CSSPlugin.logError(e.getMessage(), e);
		}

		return result;
	}
}
