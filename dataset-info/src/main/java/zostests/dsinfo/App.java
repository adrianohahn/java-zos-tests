package zostests.dsinfo;

import com.ibm.jzos.Format1DSCB;
import com.ibm.jzos.JFCB;
import com.ibm.jzos.ZFile;
import com.ibm.jzos.ZFileException;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Print attributes of the dataset associated with the DD names passed as parameters.
 */
public class App {


    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.printf("Usage: %s <DDNAME1> [DDNAME2] [...]", App.class.getName());
            System.exit(8);
        }
        new App().execute(Arrays.stream(args));
    }

    private void execute(Stream<String> ddNames) {
        ddNames.forEach(this::printDatasetDetails);
    }

    private void printDatasetDetails(String ddName) {
        System.out.printf("DD:%s%n", ddName);
        try {
            printDatasetDetailsWithZFile(ddName);
            printDataSetDetailsWithJFCBAndDSCB(ddName);
        } catch (ZFileException e) {
            throw new RuntimeException(e);
        }

    }

    private void printDataSetDetailsWithJFCBAndDSCB(String ddName) throws ZFileException {
        JFCB jfcb = ZFile.readJFCB(ddName);
        printDatasetDetails(
                JFCB.class.getSimpleName(),
                jfcb.getJfcbdsnm(),
                jfcb.getJfcbvols(),
                jfcb.getJfcblksi(),
                jfcb.getJfclrecl(),
                String.valueOf(jfcb.getJfcrecfm()));
        Format1DSCB dscb = ZFile.obtainDSN(String.format("'%s'", jfcb.getJfcbdsnm()), jfcb.getJfcbvols());
        printDatasetDetails(
                Format1DSCB.class.getSimpleName(),
                dscb.getDS1DSNAM(),
                dscb.getDS1DSSN(),
                dscb.getDS1BLKL(),
                dscb.getDS1LRECL(),
                String.valueOf(dscb.getDS1RECFM()));
    }

    private static void printDatasetDetailsWithZFile(String ddName) throws ZFileException {
        ZFile zFile = new ZFile(String.format("//DD:%s", ddName), "r");
        printDatasetDetails(
                ZFile.class.getSimpleName(),
                zFile.getActualFilename(),
                "N/A", zFile.getBlksize(),
                zFile.getLrecl(),
                zFile.getRecfm());
        zFile.close();
    }

    private static void printDatasetDetails(String className, String dsname, String volser, int blksize, int lrecl, String recfm) {
        System.out.printf(
                "Dataset details using %s methods%n" +
                "\tDSNAME.: %s%n" +
                "\tVOLSER.: %s%n" +
                "\tBLKSIZE: %d%n" +
                "\tLRECL..: %d%n" +
                "\tRECFM..: %s%n",
                className,
                dsname,
                volser,
                blksize,
                lrecl,
                recfm);
    }
}
