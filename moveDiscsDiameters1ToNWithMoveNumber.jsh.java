void moveDiscsDiameters1ToN(int n, String startPeg,
        String sparePeg, String endPeg, BigInteger[] moveNumber) {
    if (n == 1) {
        System.out.printf("%d Move top disc from %s to top of %s.%n",
                moveNumber[0] = moveNumber[0].add(BigInteger.ONE), startPeg,
                endPeg);
    } else {
        var nMinus1 = n - 1;
        moveDiscsDiameters1ToN(nMinus1, startPeg, endPeg, sparePeg,
                moveNumber);
        System.out.printf("%d Move top disc from %s to top of %s.%n",
                moveNumber[0] = moveNumber[0].add(BigInteger.ONE), startPeg,
                endPeg);
        moveDiscsDiameters1ToN(nMinus1, sparePeg, startPeg, endPeg,
                moveNumber);
    }
}
moveDiscsDiameters1ToN(3, "middle peg", "right peg", "left peg",
        new BigInteger[]{BigInteger.ZERO});
