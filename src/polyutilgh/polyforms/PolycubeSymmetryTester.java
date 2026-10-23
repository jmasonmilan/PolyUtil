package polyutilgh.polyforms;
import polyutilgh.CoordTriplet;
import java.util.Arrays;
/**
 *
 * @author mason
 */
public class PolycubeSymmetryTester {
    public static long ops;
    GenericPolycubeInterface gp;
    boolean[] ignoreTable;
    public PolycubeSymmetryTester(GenericPolycubeInterface gp) {
        this.gp = gp;
        ignoreTable = new boolean[9];
    }
    // E : reflection in face
    public int symEcount() {
        ops += 3;
        int ret = 0;
        if (symEx())
            ret++;
        if (symEy())
            ret++;
        if (symEz())
            ret++;
        return ret;
    }
    public boolean symEx() {
        return symE(0, 1, 2);
    }
    public boolean symEy() {
        return symE(1, 0, 2);
    }
    public boolean symEz() {
        return symE(2, 0, 1);
    }
    boolean symE(int para, int other1, int other2) {
        int min = gp.getMin(para), max = gp.getMax(para);
        for (int i = 0; i < gp.size(); i++) {
            //CoordTriplet ct = gp.getCoords(i);
            //int v = ct.getCoord(para);
            int v = gp.getCoord(i, para);
            int nv = max - v + min;
            //CoordTriplet nct = new CoordTriplet(para, nv, other1, ct.getCoord(other1), other2, ct.getCoord(other2));
            //if (!gp.contains(nct))
            if (!contains(para, nv, other1, gp.getCoord(i, other1), other2, gp.getCoord(i, other2)))
                return false;
        }
        return true;
    }
    // 90 degree rotation about centre of face
    public int symAcount() {
        ops += 3;
        int ret = 0;
        if (symAxy())
            ret += 2;
        if (symAxz())
            ret += 2;
        if (symAyz())
            ret += 2;
        return ret;
    }
    public boolean symAxy() {
        return symA(2, 0, 1);
    }
    public boolean symAxz() {
        return symA(1, 0, 2);
    }
    public boolean symAyz() {
        return symA(0, 1, 2);
    }
    boolean symA(int fixed, int other1, int other2) {
        return symA(fixed, other1, other2, false);
    }
    public int symJcount() {
        ops += 3;
        int ret = 0;
        if (symJxy())
            ret += 2;
        if (symJxz())
            ret += 2;
        if (symJyz())
            ret += 2;
        return ret;
    }
    public boolean symJxy() {
        return symJ(2, 0, 1);
    }
    public boolean symJxz() {
        return symJ(1, 0, 2);
    }
    public boolean symJyz() {
        return symJ(0, 1, 2);
    }
    boolean symJ(int fixed, int other1, int other2) {
        return symA(fixed, other1, other2, true);
    }
    boolean symA(int fixed, int other1, int other2, boolean doE) {
        int min1 = gp.getMin(other1), max1 = gp.getMax(other1);
        int min2 = gp.getMin(other2);
        int maxFixed = gp.getMax(fixed);
        int minFixed = gp.getMin(fixed);
        for (int i = 0; i < gp.size(); i++) {
            //CoordTriplet ct = gp.getCoords(i);
            //int v1 = ct.getCoord(other1);
            //int v2 = ct.getCoord(other2);
            int v1 = gp.getCoord(i, other1);
            int v2 = gp.getCoord(i, other2); 
            int nv1, nv2;
            
            nv1 = max1 - (v2 - min2);
            nv2 = v1 - min1 + min2;
            
            int nvf;
            if (doE)
                //nvf = maxFixed - (ct.getCoord(fixed) - minFixed);
                nvf = maxFixed - (gp.getCoord(i, fixed) - minFixed);
            else
                //nvf = ct.getCoord(fixed);
                nvf = gp.getCoord(i, fixed);
            
            //CoordTriplet nct = new CoordTriplet(other1, nv1, other2, nv2, fixed, nvf);
            //if (!gp.contains(nct))
            if (!contains(other1, nv1, other2, nv2, fixed, nvf))
                return false;
        }
        return true;        
    }
    // 180 degree rotation about centre of face
    public int symBcount() {
        ops += 3;
        int ret = 0;
        if (symBxy())
            ret += 1;
        if (symBxz())
            ret += 1;
        if (symByz())
            ret += 1;
        return ret;
    }
    public boolean symBxy() {
        return symB(2, 0, 1);
    }
    public boolean symBxz() {
        return symB(1, 0, 2);
    }
    public boolean symByz() {
        return symB(0, 1, 2);
    }
    boolean symB(int fixed, int other1, int other2) {
    
        int min1 = gp.getMin(other1), max1 = gp.getMax(other1);
        int min2 = gp.getMin(other2), max2 = gp.getMax(other2);
        
        for (int i = 0; i < gp.size(); i++) {
            //CoordTriplet ct = gp.getCoords(i);
            //int v1 = ct.getCoord(other1);
            //int v2 = ct.getCoord(other2);
            int v1 = gp.getCoord(i, other1);
            int v2 = gp.getCoord(i, other2); 
            int nv1, nv2;
            
            nv1 = max1 - (v1 - min1);
            nv2 = max2 - (v2 - min2);
            
            //int nvf = ct.getCoord(fixed);
            int nvf = gp.getCoord(i, fixed);
            
            //CoordTriplet nct = new CoordTriplet(other1, nv1, other2, nv2, fixed, nvf);
            //if (!gp.contains(nct))
            if (!contains(other1, nv1, other2, nv2, fixed, nvf))
                return false;
        }
        return true;        
    }
    // reflection in plane through opposite edges
    public int symFcount() {
        ops += 6;
        int ret = 0;
        if (symFxyU())
            ret++;
        if (symFxyD())
            ret++;
        if (symFxzU())
            ret++;
        if (symFxzD())
            ret++;
        if (symFyzU())
            ret++;
        if (symFyzD())
            ret++;
        
        return ret;
    }    
    public boolean symFxyU() {
        return symCF(2, 0, 1, 1, false);
    }
    public boolean symFxyD() {
        return symCF(2, 0, 1, -1, false);
    }
    public boolean symFxzU() {
        return symCF(1, 0, 2, 1, false);
    }
    public boolean symFxzD() {
        return symCF(1, 0, 2, -1, false);
    }
    public boolean symFyzU() {
        return symCF(0, 1, 2, 1, false);
    }
    public boolean symFyzD() {
        return symCF(0, 1, 2, -1, false);
    }
    
    // rotation 180 degrees on axis through centres of opposite edges
    public int symCcount() {
        ops += 6;
        int ret = 0;
        if (symCxyU())
            ret++;
        if (symCxyD())
            ret++;
        if (symCxzU())
            ret++;
        if (symCxzD())
            ret++;
        if (symCyzU())
            ret++;
        if (symCyzD())
            ret++;
        
        return ret;
    }    
    public boolean symCxyU() {
        return symCF(2, 0, 1, 1, true);
    }
    public boolean symCxyD() {
        return symCF(2, 0, 1, -1, true);
    }
    public boolean symCxzU() {
        return symCF(1, 0, 2, 1, true);
    }
    public boolean symCxzD() {
        return symCF(1, 0, 2, -1, true);
    }
    public boolean symCyzU() {
        return symCF(0, 1, 2, 1, true);
    }
    public boolean symCyzD() {
        return symCF(0, 1, 2, -1, true);
    }
    
    
    boolean symCF(int fixed, int other1, int other2, int dir, boolean doC) {
        int min1 = gp.getMin(other1), max1 = gp.getMax(other1);
        int min2 = gp.getMin(other2), max2 = gp.getMax(other2);
        int maxFixed = gp.getMax(fixed);
        int minFixed = gp.getMin(fixed);        
        for (int i = 0; i < gp.size(); i++) {
            //CoordTriplet ct = gp.getCoords(i);
            //int v1 = ct.getCoord(other1);
            //int v2 = ct.getCoord(other2);
            int v1 = gp.getCoord(i, other1);
            int v2 = gp.getCoord(i, other2);            
            int nv1, nv2;
            if (dir == 1) {
                nv1 = v2 - min2 + min1;
                nv2 = v1 - min1 + min2;
            } else {
                nv1 = max1 - (v2 - min2);
                nv2 = max2 - (v1 - min1);
            }
            
            //int nvf = ct.getCoord(fixed);
            int nvf = gp.getCoord(i, fixed);
            if (doC)
                nvf = maxFixed - (nvf - minFixed);
            
            //CoordTriplet nct = new CoordTriplet(other1, nv1, other2, nv2, fixed, nvf);
            //if (!gp.contains(nct))
            if (!contains(other1, nv1, other2, nv2, fixed, nvf))
                return false;
        }
        return true;
    }
    boolean contains(int other1, int nv1, int other2, int nv2, int fixed, int nvf) {
        int x = get(0, other1, nv1, other2, nv2, fixed, nvf);
        int y = get(1, other1, nv1, other2, nv2, fixed, nvf);
        int z = get(2, other1, nv1, other2, nv2, fixed, nvf);
        return gp.contains(x, y, z);
    }
    int get(int c, int c1, int v1, int c2, int v2, int c3, int v3)    {
        if (c == c1)
            return v1;
        if (c == c2)
            return v2;
        return v3;
    }
    // reflection in centre point
    boolean symK() {
        int minX = gp.getMin(0), minY = gp.getMin(1), minZ = gp.getMin(2);
        int maxX = gp.getMax(0), maxY = gp.getMax(1), maxZ = gp.getMax(2);
        for (int i = 0; i < gp.size(); i++) {
            //CoordTriplet ct = gp.getCoords(i);
            //int x = ct.getX(), y = ct.getY(), z = ct.getZ();
            int x = gp.getX(i), y = gp.getY(i), z = gp.getZ(i);
            int nx = maxX - (x - minX);
            int ny = maxY - (y - minY);
            int nz = maxZ - (z - minZ);
            
            //CoordTriplet nct = new CoordTriplet(nx, ny, nz);
            //if (!gp.contains(nct))
            if (!gp.contains(nx, ny, nz))
                return false;
        }
        return true;        
    }
    public int symKcount() {
        ops += 1;
        return symK() ? 1 : 0;
    }
    // 120 degree rotation about opposite vertices
    public int symDcount() {
        ops += 4;
        int ret = 0;
        if (symD(0, 0))
            ret += 2;
        if (symD(0, 1))
            ret += 2;
        if (symD(1, 0))
            ret += 2;
        if (symD(1, 1))
            ret += 2;
        return ret;
    }
    // D * K
    public int symHcount() {
        ops += 4;
        int ret = 0;
        if (symH(0, 0))
            ret += 2;
        if (symH(0, 1))
            ret += 2;
        if (symH(1, 0))
            ret += 2;
        if (symH(1, 1))
            ret += 2;
        return ret;
    }
    boolean symD(int xp, int yp) {
        return symD(xp, yp, false);
    }
    boolean symH(int xp, int yp) {
        return symD(xp, yp, true);
    }
    boolean symD(int xp, int yp, boolean doK) {
        int minX = gp.getMin(0), minY = gp.getMin(1), minZ = gp.getMin(2);
        int maxX = gp.getMax(0), maxY = gp.getMax(1), maxZ = gp.getMax(2);   
        
        for (int i = 0; i < gp.size(); i++) {
            //CoordTriplet ct = gp.getCoords(i);
            //int x = ct.getX(), y = ct.getY(), z = ct.getZ();
            int x = gp.getX(i), y = gp.getY(i), z = gp.getZ(i);
            
            int dx, dy, dz = z - minZ;
            
            if (xp == 0) 
                dx = x - minX;
            else
                dx = maxX - x;
            
            if (yp == 0) 
                dy = y - minY;
            else
                dy = maxY - y;
            
            int ndx = dz, ndy = dx, ndz = dy;
            
            int nx, ny, nz = minZ + ndz;
            
            if (xp == 0) 
                nx = minX + ndx;
            else
                nx = maxX - ndx;            
            
            if (yp == 0) 
                ny = minY + ndy;
            else
                ny = maxY - ndy;            

            if (doK) {
                nx = maxX - (nx - minX);
                ny = maxY - (ny - minY);
                nz = maxZ - (nz - minZ);                
            }
            
            //CoordTriplet nct = new CoordTriplet(nx, ny, nz);
            //if (!gp.contains(nct))
            if (!gp.contains(nx, ny, nz))
                return false;
        }
        return true;                
    }
    // needs verification
    public boolean achiral() {
        int e = this.symEcount();
        int f = this.symFcount();
        int h = this.symHcount();
        //int a = this.symAcount();
        int j = this.symJcount();
        boolean be = e > 0;
        //boolean ba = a > 0;
        boolean bf = f > 0;
        boolean bk = symK();
        boolean bh = h > 0;
        boolean bj = j > 0;
        
        boolean ret = be || bf || bk || bh || bj;
        return ret;
    }
    static int[][] symArray = {
        /*         A   B   C   D   E   F   H   J   K   */
    /* 0  I   */ { 0,  0,  0,  0,  0,  0,  0,  0,  0 },
    /* 1  A   */ { 2,  1,  0,  0,  0,  0,  0,  0,  0 },
    /* 2  B   */ { 0,  1,  0,  0,  0,  0,  0,  0,  0 },
    /* 3  C   */ { 0,  0,  1,  0,  0,  0,  0,  0,  0 },
    /* 4  D   */ { 0,  0,  0,  2,  0,  0,  0,  0,  0 },
    /* 5  E   */ { 0,  0,  0,  0,  1,  0,  0,  0,  0 },
    /* 6  F   */ { 0,  0,  0,  0,  0,  1,  0,  0,  0 },
    /* 7  H   */ { 0,  0,  0,  2,  0,  0,  2,  0,  1 },
    /* 8  J   */ { 0,  1,  0,  0,  0,  0,  0,  2,  0 },
    /* 9  K   */ { 0,  0,  0,  0,  0,  0,  0,  0,  1 },
    
    /* 10 BB  */ { 0,  3,  0,  0,  0,  0,  0,  0,  0 },
    /* 11 BC  */ { 0,  1,  2,  0,  0,  0,  0,  0,  0 },
    /* 12 BE  */ { 0,  1,  0,  0,  1,  0,  0,  0,  1 },
    /* 13 BF  */ { 0,  1,  0,  0,  0,  2,  0,  0,  0 },
    /* 14 CE  */ { 0,  0,  1,  0,  1,  1,  0,  0,  0 },
    /* 15 CK  */ { 0,  0,  1,  0,  0,  1,  0,  0,  1 },
    /* 16 EE  */ { 0,  1,  0,  0,  2,  0,  0,  0,  0 },
    /* 17 CD  */ { 0,  0,  3,  2,  0,  0,  0,  0,  0 },
    /* 18 FF  */ { 0,  0,  0,  2,  0,  3,  0,  0,  0 },
    
    /* 19 AB  */ { 2,  3,  2,  0,  0,  0,  0,  0,  0 },
    /* 20 AE  */ { 2,  1,  0,  0,  1,  0,  0,  2,  1 },
    /* 21 BFF */ { 0,  3,  0,  0,  0,  2,  0,  2,  0 },
    /* 22 CJ  */ { 0,  1,  2,  0,  2,  0,  0,  2,  0 },
    /* 23 EEE */ { 0,  3,  0,  0,  3,  0,  0,  0,  1 },
    /* 24 EF  */ { 2,  1,  0,  0,  2,  2,  0,  0,  0 },
    /* 25 EFF */ { 0,  1,  2,  0,  1,  2,  0,  0,  1 },
    
    /* 26 BD  */ { 0,  3,  0,  8,  0,  0,  0,  0,  0 },
    /* 27 CF  */ { 0,  0,  3,  2,  0,  3,  2,  0,  1 },
    /* 28 BBC */ { 2,  3,  2,  0,  3,  2,  0,  2,  1 },
    /* 29 CCC */ { 0,  3,  0,  8,  0,  6,  0,  6,  0 },
    /* 30 DEE */ { 0,  3,  0,  8,  3,  0,  8,  0,  1 },
    /* 31 R   */ { 6,  3,  6,  8,  0,  0,  0,  0,  0 },
    /* 32 G   */ { 6,  3,  6,  8,  3,  6,  8,  6,  1 }
    
    
            
    };
   
    static String[] nameArray = {
        "I", 
        "A", 
        "B", 
        "C", 
        "D", 
        "E", 
        "F", 
        "H", 
        "J", 
        "K", 

        "BB", 
        "BC", 
        "BE", 
        "BF", 
        "CE", 
        "CK", 
        "EE", 
        "CD", 
        "FF", 

        "AB", 
        "AE", 
        "BFF", 
        "CJ", 
        "EEE", 
        "EF", 
        "EFF", 

        "BD", 
        "CF", 
        "BBC", 
        "CCC", 
        "DEE", 
        "R", 
        "G"        
    };
    //static String[][] stringArray;
    public static int numCat = nameArray.length;
    static int[] orderArray = {
        1,4,2,2,3,2,2,6,4,2,4,4,4,4,4,4,4,6,6,8,8,8,8,8,8,8,12,12,16,24,24,24,48
    };
    static boolean[] chiralArray = {
        true,
        true,
        true,
        true,
        true,
        false,
        false,
        false,
        false,
        false,
        true,
        true,
        false,
        false,
        false,
        false,
        false,
        true,
        false,
        true,
        false,
        false,
        false,
        false,
        false,
        false,
        true,
        false,
        false,
        false,
        false,
        true,
        false
    };
    public int getOptimisedSymmetry() {
        /*if (stringArray == null) {
            stringArray = new String[9][33];
            for (int k = 0; k < 9; k++)
                for (int i = 0; i < 33; i++) {
                    String s = "";
                    for (int j = 0; j <= k; j++)
                        s += symArray[i][j];
                    stringArray[k][i] = s;
                }
            
        } */
        int[] tab = new int[9];
        
        tab[1]  = calcSym(1);
        
        if (tab[1] > 0)
            tab[0] = calcSym(0);

        
       
        for (int i = 2; i < 9; i++) {
            tab[i] = calcSym(i);
            
            if (i < 4)
                continue;
            int ret = match(tab, i);
            if (ret >= 0)
                return ret;
        }
        return 1 / 0;
    }
    int match(int[] tab, int length) {
    
        int count = 0;
        int ret = -1;
        for (int i = 0; i < 33; i++) {
            
            if (match(tab, length, i)) {
                ret = i;
                count++;
                if (count > 1)
                    return -1;
            }

        }
        if (count == 1)
            return ret;
        else
            return -1;
    }
    boolean match(int[] tab, int length, int pos) {
        for (int i = 0; i <= length; i++)
            if (tab[i] != symArray[pos][i])
                return false;
        return true;
    }
    int calcSym(int i) {
        switch(i) {
            case 0: return symAcount();
            case 1: return symBcount();
            case 2: return symCcount();
            case 3: return symDcount();
            case 4: return symEcount();
            case 5: return symFcount();
            case 6: return symHcount();
            case 7: return symJcount();
            case 8: return symKcount();
            
        }
        return 1 / 0;
    }
    public int getSymmetry() {
        int ret = -1;
        boolean found = false;
        int a = this.symAcount();
        int b = 0;
        if (!ignoreTable[1])
            b = this.symBcount();
        int c = this.symCcount();
        int d = 0;
        if (!ignoreTable[3])
            d = this.symDcount();
        int e = this.symEcount();
        int f = this.symFcount();
        int h = 0;
        if (!ignoreTable[6])
            h = this.symHcount();        
        int j = this.symJcount();   
        int k = this.symK() ? 1 : 0;
        for (int i = 0; i < 33; i++)
            if (a == symArray[i][0] &&
                (ignoreTable[1] || b == symArray[i][1]) &&
                c == symArray[i][2] &&
                (ignoreTable[3] || d == symArray[i][3]) &&
                e == symArray[i][4] &&
                f == symArray[i][5] &&
                (ignoreTable[6] || h == symArray[i][6]) &&
                j == symArray[i][7] &&
                k == symArray[i][8] 
                    ) {
                if (found) {
                    int qq = 1 / 0;
                } else {
                    ret = i;
                    found = true;
                }
            }
//        c = symCcount();
        return ret;
    }
    public PolycubeSymmetryTable getSymmetryTable() {
        PolycubeSymmetryTable t = new PolycubeSymmetryTable();
        t.sym = -1;
        boolean found = false;
        // the ignore table introduces optimisation possibilities but increases risk
        
        //if (!ignoreTable[1])
            t.b = this.symBcount();
        if (t.b > 0)
            t.a = this.symAcount();
        t.c = this.symCcount();
        
        //if (!ignoreTable[3])
            t.d = this.symDcount();
        t.e = this.symEcount();
        t.f = this.symFcount();
        
        //if (!ignoreTable[6])
            t.h = this.symHcount();        
        t.j = this.symJcount();   
        t.k = this.symK() ? 1 : 0;
        for (int i = 0; i < 33; i++)
            if (t.a == symArray[i][0] &&
                (//ignoreTable[1] || 
                    t.b == symArray[i][1]) &&
                t.c == symArray[i][2] &&
                (//ignoreTable[3] || 
                    t.d == symArray[i][3]) &&
                t.e == symArray[i][4] &&
                t.f == symArray[i][5] &&
                (//ignoreTable[6] || 
                    t.h == symArray[i][6]) &&
                t.j == symArray[i][7] &&
                t.k == symArray[i][8] 
                    ) {
                if (found) {
                    int qq = 1 / 0;
                } else {
                    t.sym = i;
                    found = true;
                }
            }
//        c = symCcount();
        return t;
    }
    public String getCentres() {
        String ret = "";
        int diffX = Math.abs(gp.getMax(0) - gp.getMin(0) );
        int diffY = Math.abs(gp.getMax(1) - gp.getMin(1) );
        int diffZ = Math.abs(gp.getMax(2) - gp.getMin(2) );
        if (this.symBxy()) {
            ret += getCentres(diffX, diffY);
        }
        if (this.symBxz()) {
            ret += getCentres(diffX, diffZ);
        }
        if (this.symByz()) {
            ret += getCentres(diffY, diffZ);
        }
        ret = sort(ret);
        return ret;
    }
    String sort(String in) {
        // Converting input string to character array
        char tempArray[] = in.toCharArray();

        // Sorting temp array using
        Arrays.sort(tempArray);

        // Returning new sorted string
        return new String(tempArray);
    }
    String getCentres(int one, int two) {
        int modOne = one % 2, modTwo = two % 2;
        if (modOne == 0 && modTwo == 0) {
           return "C";
        }
        if (modOne != 0 && modTwo != 0) {
           return "V";
        }
        return "M";
    }
    public static String getName(int i) {
        return nameArray[i];
    }
    public static int getOrder(int i) {
        return orderArray[i];
    }
    public static int getDivisor(int i) {
        return 48 / orderArray[i];
    }
    public static boolean getChirality(int i) {
        return chiralArray[i];
    }
    public void ignore(int s) {
        ignoreTable[s] = true;
    }
}
