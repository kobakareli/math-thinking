<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Calculator extends Model
{

    protected static $ops = array('+', '-', '*', '/', '^');
    protected static $opsPrecedence = array('+'=>2, '-'=>2, '*'=>1, '/'=>1, '^'=>0);

    /**
     * Break up expression into an array
     *
     * @param  String  $answer
     */
    public static function calculate($answer)
    {
        $parts = array();
        if(is_string($answer)) {
            $parts = Calculator::breakIntoParts(array(), $answer);
        }
        else {
            $parts = $answer;
        }
        while(count($parts) != 1) {
            $parts = Calculator::handleParentheses($parts);
            $parts = Calculator::calculateExpression($parts);
        }
        return $parts[0];
    }

    /**
     * Calculates parts of the expression that are in parentheses
     *
     * @param  Array  $parts - expression string broken into array
     */
    protected static function handleParentheses($parts)
    {

        $startIndx = 0;
        $startCount = 0;
        $endIndx = 0;
        $endCount = 0;

        /*if($startIndx == - 1) {
            return $parts;
        }
        if($endIndx == -1 || $endIndx < $startIndx) {
            return $parts;
        }*/
        for($i = 0; $i < count($parts); $i ++) {
            if($parts[$i] == "(") {
                if($startCount == $endCount) {
                    $startIndx = $i;
                }
                $startCount ++;
            }
            else if($parts[$i] == ")") {
                $endCount ++;
                if($endCount > $startCount) {
                    return array('error');
                }
                if($startCount == $endCount) {
                    $endIndx = $i;
                    $length = $endIndx-$startIndx+1;
                    $exp = array_slice($parts, $startIndx+1, $length-2);
                    if(count($exp) == 0) {
                        array_splice( $parts, $startIndx, $length, $exp);
                    }
                    else {
                        array_splice( $parts, $startIndx, $length, array(Calculator::calculate($exp)));
                    }
                    $i-=$length;
                }
            }
        }
        if($startCount > $endCount) {
            return array("error");
        }
        return $parts;
    }

    /**
     * calculates value of the give expression
     *
     * @param  Array  $parts - expression string broken into array
     */
    protected static function calculateExpression($parts)
    {
        $parts = Calculator::calculateByPrecedence($parts, 0);
        $parts = Calculator::calculateByPrecedence($parts, 1);
        return Calculator::calculateByPrecedence($parts, 2);
    }

    /**
     * Perform only operations that match with the given precedence
     *
     * @param  Array  $parts - expression string broken into array
     * @param  Array  $precedence - precedence number
     */
    protected static function calculateByPrecedence($parts, $precedence)
    {
        for($indx = 0; $indx < count($parts); $indx ++) {
            $part = $parts[$indx];
            if(in_array($part, Calculator::$ops)) {
                if(Calculator::$opsPrecedence[$part] == $precedence) {
                    $first = $parts[$indx-1];
                    $second = $parts[$indx+1];
                    array_splice( $parts, $indx-1, 3, array(Calculator::doOperation($first, $second, $part)));
                    $indx --;
                }
            }
        }
        return $parts;
    }

    /**
     * Break up expression into an array
     *
     * @param  String  $answer
     */
    protected static function breakIntoParts($parts, $answer)
    {
        $answer = str_replace(' ', '', $answer);
        $length = 0;
        for($i = 0; $i < strlen($answer); $i ++) {
            $char = $answer[$i];
            if(in_array($char, Calculator::$ops) || $char == '(' || $char == ')'){
                $first = substr($answer, 0, $i);
                $second = substr($answer, $i + 1);
                if($first != '') {
                    array_push($parts, $first);
                }
                array_push($parts, $char);
                return Calculator::breakIntoParts($parts, $second);
            }
        }
        if($answer != "") {
            array_push($parts, $answer);
        }
        return $parts;
    }

    /**
     * perform single operation
     *
     * @param  String  $first - 1st part of the operation
     * @param  String  $first - 2nd part of the operation
     * @param  String  $first - operand
     */
    protected static function doOperation($first, $second, $op)
    {
        switch ($op) {
            case "+":
                return strval($first + $second);
            case "-":
                return strval($first - $second);
                break;
            case "*":
                return strval($first * $second);
                break;
            case "/":
                return strval($first / $second);
            case "^":
                return strval(pow($first, $second));
                break;
        }
    }
}
