package org.telluriumsource.ui.builder

import org.telluriumsource.ui.object.GenericTable
import org.telluriumsource.ui.object.UiObject

/**
 *
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Aug 31, 2009
 *
 */

public class GenericTableBuilder extends UiObjectBuilder{
    public static final String TAG_OF_TABLE = "tagOfTable";
    public static final String TAG_OF_HEAD = "tagOfHead";
    public static final String TAG_OF_HEAD_ROW = "tagOfHeadRow";
    public static final String TAG_OF_HEAD_COLUMN = "tagOfHeadColumn";
    public static final String TAG_OF_BODY = "tagOfBody";
    public static final String TAG_OF_BODY_ROW = "tagOfBodyRow";
    public static final String TAG_OF_BODY_COLUMN = "tagOfBodyColumn";
    public static final String TAG_OF_FOOT = "tagOfFoot";
    public static final String TAG_OF_FOOT_ROW = "tagOfFootRow";
    public static final String TAG_OF_FOOT_COLUMN = "tagOfFootColumn";

  def build(Map map, Closure closure) {
    //add default parameters so that the builder can use them if not specified
    def df = [:]
    String tagOfTable = map.get(TAG_OF_TABLE)
    if (tagOfTable != null) {
      map.put(TAG, tagOfTable)
    }
//       df.put(TAG, StandardTable.TAG)
    GenericTable table = this.internBuild(new GenericTable(), map, df)

    if (tagOfTable != null) {
      table.tagOfTable = tagOfTable
    }

    String tagOfHead = map.get(TAG_OF_HEAD)
    if (tagOfHead != null) {
      table.tagOfHead = tagOfHead
    }

    String tagOfHeadRow = map.get(TAG_OF_HEAD_ROW)
    if (tagOfHeadRow != null) {
      table.tagOfHeadRow = tagOfHeadRow
    }

    String tagOfHeadColumn = map.get(TAG_OF_HEAD_COLUMN)
    if (tagOfHeadColumn != null) {
      table.tagOfHeadColumn = tagOfHeadColumn
    }

    String tagOfBody = map.get(TAG_OF_BODY)
    if (tagOfBody != null) {
      table.tagOfBody = tagOfBody
    }

    String tagOfBodyRow = map.get(TAG_OF_BODY_ROW)
    if (tagOfBodyRow != null) {
      table.tagOfBodyRow = tagOfBodyRow
    }

    String tagOfBodyColumn = map.get(TAG_OF_BODY_COLUMN)
    if (tagOfBodyColumn != null) {
      table.tagOfBodyColumn = tagOfBodyColumn
    }

    String tagOfFoot = map.get(TAG_OF_FOOT)
    if (tagOfFoot != null) {
      table.tagOfFoot = tagOfFoot
    }

    String tagOfFootRow = map.get(TAG_OF_FOOT_ROW)
    if (tagOfFootRow != null) {
      table.tagOfFootRow = tagOfFootRow
    }

    String tagOfFootColumn = map.get(TAG_OF_FOOT_COLUMN)
    if (tagOfFootColumn != null) {
      table.tagOfFootColumn = tagOfFootColumn
    }

    if (closure)
      closure(table)

    return table
  }

  def build(GenericTable table, UiObject[] objects) {

    if (table == null || objects == null || objects.length < 1)
      return table

    objects.each {UiObject obj -> table.add(obj)}

    return table
  }

  def build(GenericTable table, UiObject object) {

    if (table == null || object == null)
      return table

    table.add(object)

    return table
  }

}