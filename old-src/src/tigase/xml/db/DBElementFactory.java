/*  Package Jabber Server
 *  Copyright (C) 2001, 2002, 2003, 2004, 2005
 *  "Artur Hefczyc" <artur.hefczyc@gmail.com>
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software Foundation,
 *  Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 *
 * $Rev$
 * Last modified by $Author$
 * $Date$
 */
package tigase.xml.db;

import tigase.xml.Element;
import tigase.xml.ElementFactory;

/**
 * Describe class DBElementFactory here.
 *
 *
 * Created: Tue Oct 26 22:41:57 2004
 *
 * @author <a href="mailto:artur.hefczyc@gmail.com">Artur Hefczyc</a>
 * @version $Rev$
 */
public class DBElementFactory implements ElementFactory<DBElement> {

  private static DBElementFactory factory =
    new DBElementFactory();

  /**
   * Creates a new <code>DBElementFactory</code> instance.
   *
   */
  private DBElementFactory() { }

  // Implementation of tigase.xml.ElementFactory

  /**
   * Describe <code>elementInstance</code> method here.
   *
   * @param name a <code>String</code> value
   * @param cdata a <code>String</code> value
   * @param attnames a <code>StringBuilder[]</code> value
   * @param attvalues a <code>StringBuilder[]</code> value
   * @return an <code>DBElement</code> value
   */
  public DBElement elementInstance(final String name, final String cdata,
    final StringBuilder[] attnames, final StringBuilder[] attvalues) {
    return new DBElement(name, cdata, attnames, attvalues);
  }

  public static DBElementFactory getFactory() {
    return factory;
  }

} // DBElementFactory
