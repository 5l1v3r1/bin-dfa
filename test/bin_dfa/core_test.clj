(ns bin-dfa.core-test
  (:require [clojure.test :refer :all]
            [bin-dfa.core :refer :all]))

(deftest run-machine-test
  (letfn [(accepting [state] (or (= state "AD") (= state "ABD")))
          (not-accepting [state] (not (accepting state)))]
    (testing "run-machine"
      (testing "accept strings ending in 010 or 011"
        (let [machine (process-machine
              '((A AB A) (AB AB AC) (AC ABD AD) (ABD AB AC) (AD AB A)))]
          (is (accepting (run-machine machine "A" (parse-input "000111011"))))
          (is (accepting (run-machine machine "A" (parse-input "000111010"))))
          (is (accepting (run-machine machine "A" (parse-input "00011101010"))))
          (is (not-accepting (run-machine machine "A" (parse-input "0001110101"))))
)))))
