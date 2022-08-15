package jsast


export JsExpr.*
export JsPrefixOp.*
export JsInfixOp.*
export JsPostfixOp.*

//------------------------------------------------------------------------------
//## newtypes

final case class JsId(value:String)

//------------------------------------------------------------------------------
//## plain js expressions

/*
object JsExpr {
	object JsArray {
		object Var {
			def apply(it:JsExpr*):JsArray					= JsArray(it.toVector)
			def unapplySeq(it:JsArray):Some[Seq[JsExpr]]	= Some(it.items)
		}
	}

	object JsObject {
		object Var {
			def apply(it:(String,JsExpr)*):JsObject					= JsObject(it.toVector)
			def unapplySeq(it:JsObject):Some[Seq[(String,JsExpr)]]	= Some(it.items)
		}
	}
}
*/

enum JsExpr {
	// unital values
	case JsUndefined
	case JsNaN
	case JsNull
	case JsThis
	case JsSuper

	// literal values

	case JsBoolean(value:Boolean)
	case JsNumber(value:Double)
	case JsString(value:String)
	case JsRegexp(pattern:String, options:String)

	// groups

	case JsArray(items:Seq[JsExpr])
	case JsObject(items:Seq[(String,JsExpr)])

	// operators

	case JsPrefix(op:JsPrefixOp, child:JsExpr)
	case JsPostfix(op:JsPostfixOp, child:JsExpr)
	case JsInfix(op:JsInfixOp, left:JsExpr, right:JsExpr)

	// access

	case JsField(id:JsId)
	case JsSelect(lvalue:JsExpr, field:JsId)
	case JsAccess(lvalue:JsExpr, key:JsExpr)

	// function call

	case JsNew(target:JsExpr, arguments:Seq[JsExpr])
	case JsCall(target:JsExpr, arguments:Seq[JsExpr])

	// special

	case JsParens(sub:JsExpr)
	case JsIn(field:JsId, value:JsExpr)
	case JsTernary(condition:JsExpr, yes:JsExpr, no:JsExpr)
}

//------------------------------------------------------------------------------
//## operator enums

enum JsPrefixOp {
	// logical
	case JsNotOp

	// arithmetic
	case JsNegOp
	case JsPosOp

	// bitwise
	case JsBitNotOp


	// sideeffecting

	case JsPreIncrOp
	case JsPreDecrOp
}

enum JsInfixOp {
	// special
	case JsAssignOp
	case JsCommaOp

	// logical
	case JsAndOp
	case JsOrOp

	// comparison
	case JsEqOp
	case JsNeOp
	case JsEqEqOp
	case JsNeNeOp
	case JsGtOp
	case JsLtOp
	case JsGeOp
	case JsLeOp

	// arithmetic
	case JsAddOp
	case JsSubOp
	case JsMulOp
	case JsDivOp
	case JsModOp

	// bitwise
	case JsBitAndOp
	case JsBitOrOp
	case JsBitXorOp
	case JsBitLeftOp
	case JsBitRightOp
	case JsBitRightFillOp

	// assignment shortcut
	case JsAddAssignOp
	case JsSubAssignOp
	case JsMulAssignOp
	case JsDivAssignOp
	case JsModAssignOp

	// bitwise assignment shortcut
	case JsBitAndAssignOp
	case JsBitOrAssignOp
	case JsBitXorAssignOp
	case JsBitLeftAssignOp
	case JsBitRightAssignOp
	case JsBitRightFillAssignOp
}

enum JsPostfixOp {
	// sideeffecting
	case JsPostIncrOp
	case JsPostDecrOp
}
