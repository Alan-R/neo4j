/*
 * Copyright (c) 2002-2016 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.cypher.internal.compiler.v3_1.helpers

/**
  * Simple wrapper for a java.util.List which preserves the original list
  * while lazily converts to scala values if needed.
  * @param inner the inner java list
  * @param converter converter from java values to scala values
  */
case class JavaListWrapper[T](inner: java.util.List[T], converter: RuntimeScalaValueConverter) extends Seq[Any] {

  override def length = inner.size()

  override def iterator: Iterator[Any] = new Iterator[Any] {
    private val innerIterator = inner.iterator()
    override def hasNext: Boolean = innerIterator.hasNext

    override def next(): Any = converter.asDeepScalaValue(innerIterator.next())
  }

  override def apply(idx: Int) = converter.asDeepScalaValue(inner.get(idx))

}
