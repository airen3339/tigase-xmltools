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
 * $Author$
 * $Date$
 */

package tigase.xml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;
import tigase.annotations.TODO;

/**
 * <code>Element</code> - basic document tree node implementation.
 *  Supports Java 5.0 generic feature to make it easier to extend this class and
 *  still preserve some useful functionality. Sufficient for simple cases but
 *  probably in the most more advanced cases should be extended with additional
 *  features. Look in API documentation for more details and information about
 *  existing extensions. The most important features apart from abvious tree
 *  implementation are:
 *  <ul>
 *   <li><code>toString()</code> implementation so it can generate valid
 *    <em>XML</em> content from this element and all children.</li>
 *   <li><code>addChild(...)</code>, <code>getChild(childName)</code> supporting
 *    generic types.</li>
 *   <li><code>findChild(childPath)</code> finding child in subtree by given
 *    path to element.</li>
 *   <li><code>getChildCData(childPath)</code>, <code>getAttribute(childPath,
 *     attName)</code> returning element CData from child in subtree by given
 *    path to element.</li>
 *  </ul>
 *
 * <p>
 * Created: Mon Oct  4 17:55:16 2004
 * </p>
 * @author <a href="mailto:artur.hefczyc@gmail.com">Artur Hefczyc</a>
 * @version $Rev$
 */
@TODO(note="Make it a bit lighter.")
public class Element<E extends Element> implements Comparable<E> {

  protected String name = null;
  protected String cdata = null;
  protected Map<String, String> attributes = null;
  protected ArrayList<E> children = null;

  public Element(String argName) {
    setName(argName);
  }

  public Element(String argName, String argCData,
    StringBuilder[] att_names, StringBuilder[] att_values) {
    setName(argName);
    setCData(argCData);
    if (att_names != null) {
      setAttributes(att_names, att_values);
    } // end of if (att_names != null)
  }

  public Element(String argName, String argCData,
    String[] att_names, String[] att_values) {
    setName(argName);
    setCData(argCData);
    if (att_names != null) {
      setAttributes(att_names, att_values);
    } // end of if (att_names != null)
  }

  public ArrayList<E> getChildren() {
    return children;
  }

  public void setChildren(ArrayList<E> children) {
    this.children = children;
  }

  public String toString() {
    StringBuilder result = new StringBuilder();
    result.append("<"+name);
    if (attributes != null) {
      for (String key : attributes.keySet()) {
        result.append(" "+key+"='"+attributes.get(key)+"'");
      } // end of for ()
    } // end of if (attributes != null)
    String childrenStr = childrenToString();
    if (cdata != null || childrenStr.length() > 0) {
      result.append(">");
      if (cdata != null) {
        result.append(cdata);
      } // end of if (cdata != null)
      result.append(childrenStr);
      result.append("</"+name+">");
    } else {
      result.append("/>");
    }
    return result.toString();
  }

  public String childrenToString() {
    StringBuilder result = new StringBuilder();
    if (children != null) {
      synchronized (children) {
        for (E child : children) {
          result.append(child.toString());
        } // end of for ()
      }
    } // end of if (child != null)
    return result.toString();
  }

  public final void addChild(E child) {
    if (children == null) {
      children = new ArrayList<E>();
    } // end of if (children == null)
    synchronized (children) {
      children.add(child);
    }
  }

  public final E getChild(String name) {
    if (children != null) {
      synchronized (children) {
        for (E el : children) {
          if (el.getName().equals(name)) {
            return el;
          }
        }
      }
    } // end of if (children != null)
    return null;
  }

  public final Element findChild(String elementPath) {
    StringTokenizer strtok = new StringTokenizer(elementPath, "/", false);
    if (!strtok.nextToken().equals(getName())) {
      return null;
    } // end of if (!strtok.nextToken().equals(child.getName()))
    Element child = this;
    while (strtok.hasMoreTokens() && child != null) {
      child = child.getChild(strtok.nextToken());
    } // end of while (strtok.hasMoreTokens())
    return child;
  }

  public String getChildCData(String elementPath) {
    Element child = findChild(elementPath);
    return child != null ? child.getCData() : null;
  }

  /**
   * Get the Attributes value.
   * @return the Attributes value.
   */
  public Map<String, String> getAttributes() {
    return attributes;
  }

  /**
   * Set the Attributes value.
   * @param newAttributes The new Attributes value.
   */
  public void setAttributes(Map<String, String> newAttributes) {
    this.attributes = newAttributes;
  }

  public final String getAttribute(String attname) {
    if (attributes != null) {
      synchronized (attributes) {
        return attributes.get(attname);
      }
    } // end of if (attributes != null)
    return null;
  }

  public final String getAttribute(String elementPath, String att_name) {
    Element child = findChild(elementPath);
    return child != null ? child.getAttribute(att_name) : null;
  }

  public final void setAttribute(String elementPath,
    String att_name, String att_value) {
    Element child = findChild(elementPath);
    if (child != null) {
      child.setAttribute(att_name, att_value);
    } // end of if (child != null)
  }

  public final void setAttribute(String key, String value) {
    if (attributes == null) {
      attributes = new HashMap<String, String>();
    } // end of if (attributes == null)
    synchronized (attributes) {
      attributes.put(key, value);
    }
  }

  public void setAttributes(StringBuilder[] names, StringBuilder[] values) {
    attributes = new HashMap<String, String>();
    synchronized (attributes) {
      for (int i = 0; i < names.length; i++) {
        if (names[i] != null) {
          attributes.put(names[i].toString(), values[i].toString());
        } // end of if (names[i] != null)
      } // end of for (int i = 0; i < names.length; i++)
    }
  }

  public void setAttributes(String[] names, String[] values) {
    attributes = new HashMap<String, String>();
    synchronized (attributes) {
      for (int i = 0; i < names.length; i++) {
        if (names[i] != null) {
          attributes.put(names[i], values[i]);
        } // end of if (names[i] != null)
      } // end of for (int i = 0; i < names.length; i++)
    }
  }

  /**
   * Gets the value of name
   *
   * @return the value of name
   */
  public String getName()  {
    return this.name;
  }

  /**
   * Sets the value of name
   *
   * @param argName Value to assign to this.name
   */
  public void setName(String argName) {
    this.name = argName;
  }

  /**
   * Gets the value of cdata
   *
   * @return the value of cdata
   */
  public String getCData()  {
    return this.cdata;
  }

  /**
   * Sets the value of cdata
   *
   * @param argCData Value to assign to this.cdata
   */
  public void setCData(String argCData) {
    this.cdata = argCData;
  }

  // Implementation of java.lang.Comparable

  /**
   * Method <code>compareTo</code> is used to perform 
   *
   * @param elem an <code>Object</code> value
   * @return an <code>int</code> value
   */
  public final int compareTo(final E elem) {
    return name.compareTo(elem.getName());
  }

}// Element
