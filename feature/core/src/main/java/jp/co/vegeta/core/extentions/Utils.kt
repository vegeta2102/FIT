package jp.co.vegeta.core.extentions

/**
 * Created by vegeta on 2021/08/15.
 */

fun <R, A, B> withNoNulls(p1: A?, p2: B?, function: (p1: A, p2: B) -> R): R? =
    p1?.let { p2?.let { function.invoke(p1, p2) } }

fun <R, A, B, C> withNoNulls(p1: A?, p2: B?, p3: C?, function: (p1: A, p2: B, p3: C) -> R): R? =
    p1?.let { p2?.let { p3?.let { function.invoke(p1, p2, p3) } } }

fun <R, A, B, C, D> withNoNulls(
    p1: A?,
    p2: B?,
    p3: C?,
    p4: D?,
    function: (p1: A, p2: B, p3: C, p4: D) -> R
): R? = p1?.let { p2?.let { p3?.let { p4?.let { function.invoke(p1, p2, p3, p4) } } } }

fun <R, A, B, C, D, E> withNoNulls(
    p1: A?,
    p2: B?,
    p3: C?,
    p4: D?,
    p5: E?,
    function: (p1: A, p2: B, p3: C, p4: D, p5: E) -> R
): R? =
    p1?.let { p2?.let { p3?.let { p4?.let { p5?.let { function.invoke(p1, p2, p3, p4, p5) } } } } }