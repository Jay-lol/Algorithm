// * + - 의 연산자 순서를 정해서(6가지의 경우) 절대값이 가장 큰 경우의 절대값을 출력
// 많이 어렵다... 스택9개를 사용했는데 스택6개써서 해도 가능할듯.. 최우선순위 연산자 앞에오는 숫자를 스택말고 변수에 담아서 처리하는 식으로
// 한번의 읽기로 처리가 가능한 프로그램

import java.util.*

class Solution() {
    fun solution(expression: String): Long {
        var answer: Long = 0

        var insert = expression
        var temp = ""
        var max :Long = 0
        var lastStackMember : String? = null
        var lastStackMember2 : String? = null
        var lastStackMember3 : String? = null

        var oneMultiple = Stack<String>() // *+-
        var onePlus = Stack<String>() // *+-
        var oneMinus = Stack<String>() // *-+

        var twoPlus = Stack<String>() // + *-
        var twoMultiple = Stack<String>() // + *-
        var twoMinus = Stack<String>() // + -*

        var threeMinus = Stack<String>() // - *+
        var threeMutilple = Stack<String>() // - *+
        var threePlus = Stack<String>() // - +*

        for (i in insert.indices) {

            if (insert[i].toInt() in 48..57) {
                temp += insert[i]
            } else {
                // 기호를 만나면

                when (insert[i]) {
                    '*' -> {
                        // * 우선 케이스 oneMultiple 에 푸쉬
                        if (oneMultiple.isNotEmpty()) {
                            oneMultiple.push((oneMultiple.pop().toLong() * temp.toLong()).toString())
                        } else {
                            oneMultiple.push(temp)
                        } // * 우선 케이스

                        // + *-
                        if (twoPlus.isNotEmpty()) {
                            val a = (twoPlus.peek().toLong() + temp.toLong())
                            if (twoMultiple.isNotEmpty()) {
                                if (twoMultiple.peek() == "*") {
                                    twoMultiple.pop()
                                    twoMultiple.push((twoMultiple.pop().toLong() * a).toString())
                                    twoMultiple.push("*")
                                } else {
                                    twoMultiple.push(a.toString())
                                    twoMultiple.push("*")
                                }
                            } else {
                                twoMultiple.push(a.toString())
                                twoMultiple.push("*")
                            }
                        } else {
                            if (twoMultiple.isNotEmpty()) {
                                if (twoMultiple.peek() == "*") {
                                    twoMultiple.pop()
                                    twoMultiple.push((onePlus.pop().toLong() * temp.toLong()).toString())
                                    twoMultiple.push("*")
                                } else {
                                    twoMultiple.push((temp.toLong()).toString())
                                    twoMultiple.push("*")
                                }
                            }else {
                                twoMultiple.push((temp.toLong().toString()))
                                twoMultiple.push("*")
                            }
                        } // + *-

                        // + -*
                        if (twoPlus.isNotEmpty()) {
                            val a = (twoPlus.pop().toLong() + temp.toLong())
                            if (twoMinus.isNotEmpty()) {
                                if (twoMinus.peek() == "-") {
                                    twoMinus.pop()
                                    twoMinus.push((twoMinus.pop().toLong() - a).toString())
                                    twoMinus.push("*")
                                } else {
                                    twoMinus.push(a.toString())
                                    twoMinus.push("*")
                                }
                            } else {
                                twoMinus.push(a.toString())
                                twoMinus.push("*")
                            }
                        } else {
                            if (twoMinus.isNotEmpty()) {
                                if (twoMinus.peek() == "-") {
                                    twoMinus.pop()
                                    twoMinus.push((twoMinus.pop().toLong() - temp.toLong()).toString())
                                    twoMinus.push("*")
                                } else {
                                    twoMinus.push((temp.toLong()).toString())
                                    twoMinus.push("*")
                                }
                            } else {
                                twoMinus.push((temp.toLong().toString()))
                                twoMinus.push("*")
                            }
                        } // + -*

                        // - *+
                        if (threeMinus.isNotEmpty()) {
                            val a = (threeMinus.peek().toLong() - temp.toLong())
                            if (threeMutilple.isNotEmpty()) {
                                if (threeMutilple.peek() == "*") {
                                    threeMutilple.pop()
                                    threeMutilple.push((threeMutilple.pop().toLong() * a).toString())
                                    threeMutilple.push("*")
                                } else {
                                    threeMutilple.push(a.toString())
                                    threeMutilple.push("*")
                                }
                            } else {
                                threeMutilple.push(a.toString())
                                threeMutilple.push("*")
                            }
                        } else {
                            if (threeMutilple.isNotEmpty()) {
                                if (threeMutilple.peek() == "*") {
                                    threeMutilple.pop()
                                    threeMutilple.push((threeMutilple.pop().toLong() * temp.toLong()).toString())
                                    threeMutilple.push("*")
                                } else {
                                    threeMutilple.push((temp.toLong()).toString())
                                    threeMutilple.push("*")
                                }
                            } else {
                                threeMutilple.push((temp.toLong().toString()))
                                threeMutilple.push("*")
                            }
                        }

                        // - +*
                        if (threeMinus.isNotEmpty()) {
                            val a = (threeMinus.pop().toLong() - temp.toLong())
                            if (threePlus.isNotEmpty()) {
                                if (threePlus.peek() == "+") {
                                    threePlus.pop()
                                    threePlus.push((threePlus.pop().toLong() + a).toString())
                                    threePlus.push("*")
                                } else {
                                    threePlus.push(a.toString())
                                    threePlus.push("*")
                                }
                            } else {
                                threePlus.push(a.toString())
                                threePlus.push("*")
                            }
                        } else {
                            if (threePlus.isNotEmpty()) {
                                if (threePlus.peek() == "+") {
                                    threePlus.pop()
                                    threePlus.push((threePlus.pop().toLong() + temp.toLong()).toString())
                                    threePlus.push("*")
                                } else {
                                    threePlus.push((temp.toLong()).toString())
                                    threePlus.push("*")
                                }
                            } else {
                                threePlus.push((temp.toLong().toString()))
                                threePlus.push("*")
                            }
                        } // - +*


                        temp = ""
                    }

                    '+' -> {
                        // * 우선 케이스경우 * +-
                        // oneMultiple이 비어있으면 onePluss에 푸쉬..의 탑에 +가있다면 계산후 푸쉬... +가 우선순위 2순위이니까
                        if (oneMultiple.isNotEmpty()) {
                            val a = (oneMultiple.peek().toLong() * temp.toLong())
                            if (onePlus.isNotEmpty()) {
                                if (onePlus.peek() == "+") {
                                    onePlus.pop()
                                    onePlus.push((onePlus.pop().toLong() + a).toString())
                                    onePlus.push("+")
                                } else {
                                    onePlus.push(a.toString())
                                    onePlus.push("+")
                                }
                            } else {
                                onePlus.push(a.toString())
                                onePlus.push("+")
                            }
                        } else {
                            if (onePlus.isNotEmpty()) {
                                if (onePlus.peek() == "+") {
                                    onePlus.pop()
                                    onePlus.push((onePlus.pop().toLong() + temp.toLong()).toString())
                                    onePlus.push("+")
                                } else {
                                    onePlus.push((temp.toLong()).toString())
                                    onePlus.push("+")
                                }
                            } else {
                                onePlus.push((temp.toLong().toString()))
                                onePlus.push("+")
                            }
                        }// * 우선 케이스

                        // * -+
                        if (oneMultiple.isNotEmpty()) {
                            val a = (oneMultiple.pop().toLong() * temp.toLong())
                            if (oneMinus.isNotEmpty()) {
                                if (oneMinus.peek() == "-") {
                                    oneMinus.pop()
                                    oneMinus.push((oneMinus.pop().toLong() - a).toString())
                                    oneMinus.push("+")
                                } else {
                                    oneMinus.push(a.toString())
                                    oneMinus.push("+")
                                }
                            } else {
                                oneMinus.push(a.toString())
                                oneMinus.push("+")
                            }
                        } else {
                            if (oneMinus.isNotEmpty()) {
                                if (oneMinus.peek() == "-") {
                                    oneMinus.pop()
                                    oneMinus.push((oneMinus.pop().toLong() - temp.toLong()).toString())
                                    oneMinus.push("+")
                                } else {
                                    oneMinus.push((temp.toLong()).toString())
                                    oneMinus.push("+")
                                }
                            } else {
                                oneMinus.push((temp.toLong().toString()))
                                oneMinus.push("+")
                            }
                        }// * -+ 우선 케이스

                        // + 우선 케이스 twoPlus 에 푸쉬
                        if (twoPlus.isNotEmpty()) {
                            twoPlus.push((twoPlus.pop().toLong() + temp.toLong()).toString())
                        } else {
                            twoPlus.push(temp)
                        } // + 우선 케이스



                        // - *+
                        if (threeMinus.isNotEmpty()) {
                            val a = (threeMinus.peek().toLong() - temp.toLong())
                            if (threeMutilple.isNotEmpty()) {
                                if (threeMutilple.peek() == "*") {
                                    threeMutilple.pop()
                                    threeMutilple.push((threeMutilple.pop().toLong() * a).toString())
                                    threeMutilple.push("+")
                                } else {
                                    threeMutilple.push(a.toString())
                                    threeMutilple.push("+")
                                }
                            } else {
                                threeMutilple.push(a.toString())
                                threeMutilple.push("+")
                            }
                        } else {
                            if (threeMutilple.isNotEmpty()) {
                                if (threeMutilple.peek() == "*") {
                                    threeMutilple.pop()
                                    threeMutilple.push((threeMutilple.pop().toLong() * temp.toLong()).toString())
                                    threeMutilple.push("+")
                                } else {
                                    threeMutilple.push((temp.toLong()).toString())
                                    threeMutilple.push("+")
                                }
                            } else {
                                threeMutilple.push((temp.toLong().toString()))
                                threeMutilple.push("+")
                            }
                        }



                        // - +*
                        if (threeMinus.isNotEmpty()) {
                            val a = (threeMinus.pop().toLong() - temp.toLong())
                            if (threePlus.isNotEmpty()) {
                                if (threePlus.peek() == "+") {
                                    threePlus.pop()
                                    threePlus.push((threePlus.pop().toLong() + a).toString())
                                    threePlus.push("+")
                                } else {
                                    threePlus.push(a.toString())
                                    threePlus.push("+")
                                }
                            } else {
                                threePlus.push(a.toString())
                                threePlus.push("+")
                            }
                        } else {
                            if (threePlus.isNotEmpty()) {
                                if (threePlus.peek() == "+") {
                                    threePlus.pop()
                                    threePlus.push((threePlus.pop().toLong() + temp.toLong()).toString())
                                    threePlus.push("+")
                                } else {
                                    threePlus.push((temp.toLong()).toString())
                                    threePlus.push("+")
                                }
                            } else {
                                threePlus.push((temp.toLong().toString()))
                                threePlus.push("+")
                            }
                        }

                        temp = ""
                    }

                    '-' -> {

                        if (oneMultiple.isNotEmpty()) {
                            val a = (oneMultiple.peek().toLong() * temp.toLong())
                            if (onePlus.isNotEmpty()) {
                                if (onePlus.peek() == "+") {
                                    onePlus.pop()
                                    onePlus.push((onePlus.pop().toLong() + a).toString())
                                    onePlus.push("-")
                                } else {
                                    onePlus.push(a.toString())
                                    onePlus.push("-")
                                }
                            } else {
                                onePlus.push(a.toString())
                                onePlus.push("-")
                            }
                        } else {
                            if (onePlus.isNotEmpty()) {
                                if (onePlus.peek() == "+") {
                                    onePlus.pop()
                                    onePlus.push((onePlus.pop().toLong() + temp.toLong()).toString())
                                    onePlus.push("-")
                                } else {
                                    onePlus.push((temp.toLong()).toString())
                                    onePlus.push("-")
                                }
                            }else {
                                onePlus.push((temp.toLong().toString()))
                                onePlus.push("-")
                            }
                        } // * 우선 케이스

                        // * -+
                        if (oneMultiple.isNotEmpty()) {
                            val a = (oneMultiple.pop().toLong() * temp.toLong())
                            if (oneMinus.isNotEmpty()) {
                                if (oneMinus.peek() == "-") {
                                    oneMinus.pop()
                                    oneMinus.push((oneMinus.pop().toLong() - a).toString())
                                    oneMinus.push("-")
                                } else {
                                    oneMinus.push(a.toString())
                                    oneMinus.push("-")
                                }
                            } else {
                                oneMinus.push(a.toString())
                                oneMinus.push("-")
                            }
                        } else {
                            if (oneMinus.isNotEmpty()) {
                                if (oneMinus.peek() == "-") {
                                    oneMinus.pop()
                                    oneMinus.push((oneMinus.pop().toLong() - temp.toLong()).toString())
                                    oneMinus.push("-")
                                } else {
                                    oneMinus.push((temp.toLong()).toString())
                                    oneMinus.push("-")
                                }
                            } else {
                                oneMinus.push((temp.toLong().toString()))
                                oneMinus.push("-")
                            }
                        } // * -+

                        // + *-
                        if (twoPlus.isNotEmpty()) {
                            val a = (twoPlus.peek().toLong() + temp.toLong())
                            if (twoMultiple.isNotEmpty()) {
                                if (twoMultiple.peek() == "*") {
                                    twoMultiple.pop()
                                    twoMultiple.push((twoMultiple.pop().toLong() * a).toString())
                                    twoMultiple.push("-")
                                } else {
                                    twoMultiple.push(a.toString())
                                    twoMultiple.push("-")
                                }
                            } else {
                                twoMultiple.push(a.toString())
                                twoMultiple.push("-")
                            }
                        } else {
                            if (twoMultiple.isNotEmpty()) {
                                if (twoMultiple.peek() == "*") {
                                    twoMultiple.pop()
                                    twoMultiple.push((twoMultiple.pop().toLong() * temp.toLong()).toString())
                                    twoMultiple.push("-")
                                } else {
                                    twoMultiple.push((temp.toLong()).toString())
                                    twoMultiple.push("-")
                                }
                            }else {
                                twoMultiple.push((temp.toLong().toString()))
                                twoMultiple.push("-")
                            }
                        } // + *-

                        // + -*
                        if (twoPlus.isNotEmpty()) {
                            val a = (twoPlus.pop().toLong() + temp.toLong())
                            if (twoMinus.isNotEmpty()) {
                                if (twoMinus.peek() == "-") {
                                    twoMinus.pop()
                                    twoMinus.push((twoMinus.pop().toLong() - a).toString())
                                    twoMinus.push("-")
                                } else {
                                    twoMinus.push(a.toString())
                                    twoMinus.push("-")
                                }
                            } else {
                                twoMinus.push(a.toString())
                                twoMinus.push("-")
                            }
                        } else {
                            if (twoMinus.isNotEmpty()) {
                                if (twoMinus.peek() == "-") {
                                    twoMinus.pop()
                                    twoMinus.push((twoMinus.pop().toLong() - temp.toLong()).toString())
                                    twoMinus.push("-")
                                } else {
                                    twoMinus.push((temp.toLong()).toString())
                                    twoMinus.push("-")
                                }
                            } else {
                                twoMinus.push((temp.toLong().toString()))
                                twoMinus.push("-")
                            }
                        } // + -*


                        // - 우선 케이스 threeMinus 에 푸쉬
                        if (threeMinus.isNotEmpty()) {
                            threeMinus.push((threeMinus.pop().toLong() - temp.toLong()).toString())
                        } else {
                            threeMinus.push(temp)
                        } // - 우선 케이스


                        temp = ""
                    }

                }

            }


        } // end of all case

        // 마지막 숫자 힘들어서 ㅠ
        var temp2 = temp
        var temp3 = temp
        var temp4 = temp
        var temp5 = temp
        var temp6 = temp

        while (oneMultiple.isNotEmpty()){
            lastStackMember = oneMultiple.peek()
            temp = (oneMultiple.pop().toLong() * temp.toLong()).toString()
        }

        while (onePlus.isNotEmpty()){
            if(onePlus.peek()=="+"){
                onePlus.pop()
                temp = (onePlus.pop().toLong() + temp.toLong()).toString()
            } else
                oneMultiple.push(onePlus.pop())     // 순서대로 계산하려고 뒤집기.다만 제일위가 우선순위 먼저인경우로 끝날경우를 처리해줌
        }

        // temp 는 덧셈까지만 끝난상태임

        while (oneMultiple.isNotEmpty()){
            if (oneMultiple.size != 2)
            {
                var a = oneMultiple.pop().toLong()
                oneMultiple.pop()
                oneMultiple.push((a - oneMultiple.pop().toLong()).toString())
            } else {
                var a = oneMultiple.pop().toLong()
                oneMultiple.pop()
                temp = (a - temp.toLong()).toString()
            }
        }

        answer = maxOf(Math.abs(temp.toLong()), max)

        // *+- 끝

        // *-+ 시작
        if(lastStackMember!=null){
            temp2 = (lastStackMember.toLong() * temp2.toLong()).toString()
        }

        while (oneMinus.isNotEmpty()){
            if(oneMinus.peek()=="-"){
                oneMinus.pop()
                temp2 = (oneMinus.pop().toLong() - temp2.toLong()).toString()
            } else
                oneMultiple.push(oneMinus.pop())     // 순서대로 계산하려고 뒤집기.다만 제일위가 우선순위 먼저인경우로 끝날경우를 처리해줌
        }

        while (oneMultiple.isNotEmpty()){
            if (oneMultiple.size != 2)
            {
                var a = oneMultiple.pop().toLong()
                oneMultiple.pop()
                oneMultiple.push((a + oneMultiple.pop().toLong()).toString())

            } else {
                var a = oneMultiple.pop().toLong()
                oneMultiple.pop()
                temp2 = (a + temp2.toLong()).toString()

            }
        }

        answer = maxOf(Math.abs(temp2.toLong()), answer)

        // * 우선 끝


        // 내일 + *-
        while (twoPlus.isNotEmpty()){
            lastStackMember2 = twoPlus.peek()
            temp3 = (twoPlus.pop().toLong() + temp3.toLong()).toString()
        }

        while (twoMultiple.isNotEmpty()){
            if(twoMultiple.peek()=="*"){
                twoMultiple.pop()
                temp3 = (twoMultiple.pop().toLong() * temp3.toLong()).toString()
            } else
                twoPlus.push(twoMultiple.pop())  // 순서대로 계산하려고 뒤집기.다만 제일위가 우선순위 먼저인경우로 끝날경우를 처리해줌
        }


        while (twoPlus.isNotEmpty()){
            if (twoPlus.size != 2)
            {
                var a = twoPlus.pop().toLong()
                twoPlus.pop()
                twoPlus.push((a - twoPlus.pop().toLong()).toString())

            } else {
                var a = twoPlus.pop().toLong()
                twoPlus.pop()
                temp3 = (a - temp3.toLong()).toString()
            }
        }

        answer = maxOf(Math.abs(temp3.toLong()), answer)

        // + -*
        if(lastStackMember2!=null){
            temp4 = (lastStackMember2.toLong() + temp4.toLong()).toString()
        }

        while (twoMinus.isNotEmpty()){
            if(twoMinus.peek()=="-"){
                twoMinus.pop()
                temp4 = (twoMinus.pop().toLong() - temp4.toLong()).toString()
            } else
                twoPlus.push(twoMinus.pop())      // 순서대로 계산하려고 뒤집기.다만 제일위가 우선순위 먼저인경우로 끝날경우를 처리해줌
        }

        while (twoPlus.isNotEmpty()){
            if (twoPlus.size != 2)
            {
                var a = twoPlus.pop().toLong()
                twoPlus.pop()
                twoPlus.push((a * twoPlus.pop().toLong()).toString())

            } else {
                var a = twoPlus.pop().toLong()
                twoPlus.pop()
                temp4 = (a * temp4.toLong()).toString()
            }
        }

        answer = maxOf(Math.abs(temp4.toLong()), answer)

        // - *+

        while (threeMinus.isNotEmpty()){

            lastStackMember3 = threeMinus.peek()
            temp5 = (threeMinus.pop().toLong() - temp5.toLong()).toString()
        }



        while (threeMutilple.isNotEmpty()){
            if(threeMutilple.peek()=="*"){
                threeMutilple.pop()
                temp5 = (threeMutilple.pop().toLong() * temp5.toLong()).toString()
            }
            else
                threeMinus.push(threeMutilple.pop())   // 순서대로 계산하려고 뒤집기.다만 제일위가 우선순위 먼저인경우로 끝날경우를 처리해줌
        }


        while (threeMinus.isNotEmpty()){
            if (threeMinus.size != 2)
            {
                var a = threeMinus.pop().toLong()
                threeMinus.pop()
                threeMinus.push((a + threeMinus.pop().toLong()).toString())
            } else {
                var a = threeMinus.pop().toLong()
                threeMinus.pop()
                temp5 = (a + temp5.toLong()).toString()

            }
        }

        answer = maxOf(Math.abs(temp5.toLong()), answer)



        // - +*
        if(lastStackMember3!=null){
            temp6 = (lastStackMember3.toLong() - temp6.toLong()).toString()
        }

        while (threePlus.isNotEmpty()){
            if(threePlus.peek()=="+"){
                threePlus.pop()
                temp6 = (threePlus.pop().toLong() + temp6.toLong()).toString()
            }else
                threeMinus.push(threePlus.pop())     // 순서대로 계산하려고 뒤집기.다만 제일위가 우선순위 먼저인경우로 끝날경우를 처리해줌
        }

        while (threeMinus.isNotEmpty()){
            if (threeMinus.size != 2)
            {
                var a = threeMinus.pop().toLong()
                threeMinus.pop()
                threeMinus.push((a * threeMinus.pop().toLong()).toString())

            } else {
                var a = threeMinus.pop().toLong()
                threeMinus.pop()
                temp6 = (a * temp6.toLong()).toString()
            }
        }

        answer = maxOf(Math.abs(temp6.toLong()), answer)

        return answer
    }

}

fun main() {
    println(Solution().solution("100-200*300-500+20"))
}