package jsast

import scala.collection.immutable.{ Seq => ISeq }

//------------------------------------------------------------------------------
//## newtypes

case class JSId(value:String)

//------------------------------------------------------------------------------
//## plain js expressions

// groups

sealed trait JSExpr

// literal values

case object JSUndefined	extends JSExpr
case object JSNaN		extends JSExpr
case object JSNull 		extends JSExpr
case object JSThis 		extends JSExpr
case object JSSuper		extends JSExpr

case class JSBoolean(value:Boolean) 				extends JSExpr
case class JSNumber(value:Double) 					extends JSExpr
case class JSString(value:String)					extends JSExpr
case class JSRegexp(pattern:String, options:String)	extends JSExpr
case class JSArray(items:ISeq[JSExpr]) 				extends JSExpr
case class JSObject(items:ISeq[(String,JSExpr)])	extends JSExpr

// operators

case class JSPrefix(op:JSPrefixOp, child:JSExpr)			extends JSExpr
case class JSPostfix(op:JSPostfixOp, child:JSExpr)			extends JSExpr
case class JSInfix(op:JSInfixOp, left:JSExpr, right:JSExpr)	extends JSExpr

// access

case class JSField(id:JSId)						extends JSExpr
case class JSSelect(lvalue:JSExpr, field:JSId)	extends JSExpr
case class JSAccess(lvalue:JSExpr, key:JSExpr)	extends JSExpr

// function call
		
case class JSNew(call:JSCall)								extends JSExpr
case class JSCall(target:JSExpr, arguments:ISeq[JSExpr])	extends JSExpr

// special

case class JSParens(sub:JSExpr)									extends JSExpr
case class JSIn(field:JSId, value:JSExpr)						extends JSExpr
case class JSTernary(condition:JSExpr, yes:JSExpr, no:JSExpr)	extends JSExpr

//------------------------------------------------------------------------------
//## varargs builder

object JSVarArray {
	def apply(it:JSExpr*):JSArray					= JSArray(it.toVector)
	def unapplySeq(it:JSArray):Option[ISeq[JSExpr]]	= Some(it.items)
}

object JSVarObject {
	def apply(it:(String,JSExpr)*):JSObject						= JSObject(it.toVector)
	def unapplySeq(it:JSObject):Option[ISeq[(String,JSExpr)]]	= Some(it.items)
}

//------------------------------------------------------------------------------
//## operator enums

sealed trait JSPrefixOp
sealed trait JSPostfixOp
sealed trait JSInfixOp

// special

case object JSAssignOp	extends JSInfixOp
case object JSCommaOp	extends JSInfixOp

// logical

case object JSNotOp		extends JSPrefixOp
case object JSAndOp		extends JSInfixOp
case object JSOrOp		extends JSInfixOp

// comparison

case object JSEqOp		extends JSInfixOp
case object JSNeOp		extends JSInfixOp
case object JSEqEqOp	extends JSInfixOp
case object JSNeNeOp	extends JSInfixOp
case object JSGtOp		extends JSInfixOp
case object JSLtOp		extends JSInfixOp
case object JSGeOp		extends JSInfixOp
case object JSLeOp		extends JSInfixOp

// arithmetic

case object JSNegOp	extends JSPrefixOp
case object JSPosOp	extends JSPrefixOp
case object JSAddOp	extends JSInfixOp
case object JSSubOp	extends JSInfixOp
case object JSMulOp	extends JSInfixOp
case object JSDivOp	extends JSInfixOp
case object JSModOp	extends JSInfixOp

// bitwise
case object JSBitNotOp			extends JSPrefixOp
case object JSBitAndOp			extends JSInfixOp
case object JSBitOrOp			extends JSInfixOp
case object JSBitXorOp			extends JSInfixOp
case object JSBitLeftOp			extends JSInfixOp
case object JSBitRightOp		extends JSInfixOp
case object JSBitRightFillOp	extends JSInfixOp

// assignment shortcut

case object JSAddAssignOp	extends JSInfixOp
case object JSSubAssignOp	extends JSInfixOp
case object JSMulAssignOp	extends JSInfixOp
case object JSDivAssignOp	extends JSInfixOp
case object JSModAssignOp	extends JSInfixOp

case object JSBitAndAssignOp		extends JSInfixOp
case object JSBitOrAssignOp			extends JSInfixOp
case object JSBitXorAssignOp		extends JSInfixOp
case object JSBitLeftAssignOp		extends JSInfixOp
case object JSBitRightAssignOp		extends JSInfixOp
case object JSBitRightFillAssignOp	extends JSInfixOp

// sideeffecting

case object JSPreIncrOp		extends JSPrefixOp
case object JSPreDecrOp		extends JSPrefixOp
case object JSPostIncrOp	extends JSPostfixOp
case object JSPostDecrOp	extends JSPostfixOp
