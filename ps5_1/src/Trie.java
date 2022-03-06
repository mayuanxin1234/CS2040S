import java.util.ArrayList;

public class Trie {

    // Wildcards
    final char WILDCARD = '.';
    TrieNode root;
    private class TrieNode {
        // TODO: Create your TrieNode class here.
        int[] presentChars = new int[62];
        private int value;
        private boolean end;
        TrieNode[] children;
        public TrieNode(int value) {
            this.value = value;
            this.end = false;
            children = new TrieNode[62];
        }
    }

    public Trie() {
        // TODO: Initialise a trie class here.
        this.root = new TrieNode(0);
    }

    /**
     * Inserts string s into the Trie.
     *
     * @param s string to insert into the Trie
     */
    void insert(String s) {
        // TODO
        int stringLength = s.length();
        TrieNode node = this.root;
        for (int i = 0; i < stringLength; i++) {
            int cIndex = asciiToIndex(s.charAt(i));
            int asciiValue = (int) s.charAt(i);
            if (node.children[cIndex] == null) {
                node.children[cIndex] = new TrieNode(asciiValue);
                node = node.children[cIndex];
            } else {
                node = node.children[cIndex];
            }
        }
        node.end = true;
    }

    public int asciiToIndex (int asciiValue) {
        if (asciiValue <= 57) {
            return asciiValue - 48;
        } else if (asciiValue <= 90) {
            return asciiValue - 55;
        } else {
            return asciiValue - 61;
        }
    }

    /**
     * Checks whether string s exists inside the Trie or not.
     *
     * @param s string to check for
     * @return whether string s is inside the Trie
     */
    boolean contains(String s) {
        // TODO
        int stringLength = s.length();
        TrieNode node = this.root;
        for (int i = 0; i < stringLength; i++) {
            int cIndex = asciiToIndex(s.charAt(i));
            int asciiValue = (int) s.charAt(i);
            if (node.children[cIndex] == null) {
                return false;
            } else {
                node = node.children[cIndex];
            }
        }
        return node.end;
    }

    public char getChar(int asciivalue) {
        return (char) asciivalue;
    }


    /**
     * Searches for strings with prefix matching the specified pattern sorted by lexicographical order. This inserts the
     * results into the specified ArrayList. Only returns at most the first limit results.
     *
     * @param s       pattern to match prefixes with
     * @param results array to add the results into
     * @param limit   max number of strings to add into results
     */
    void prefixSearch(String s, ArrayList<String> results, int limit) {
        // TODO
            prefixSearchHelper(s, results, limit, 0, "", this.root);
    }

    public void prefixSearchHelper(String s, ArrayList<String> results, int limit,
                                   int pos, String result, TrieNode node) {
        int stringLength = s.length();
        if (pos < stringLength) {
            if (s.charAt(pos) == WILDCARD) {
                for (TrieNode child : node.children) {
                    if (child != null) {
                        prefixSearchHelper(s, results, limit, pos + 1,
                                result + getChar(child.value), child);
                    }
                }
            } else {
                int childIndex = asciiToIndex(s.charAt(pos));
                TrieNode child = node.children[childIndex];
                if (child != null) {
                    prefixSearchHelper(s, results, limit, pos + 1,
                            result + getChar(child.value), child);
                }
            }
        } else {
                if (results.size() < limit) {
                    if (node.end) {
                        results.add(result);
                    }
            }
            for (TrieNode child: node.children) {
                if (child != null) {
                    prefixSearchHelper(s, results, limit, pos,
                            result + getChar(child.value), child);
                }
            }
        }
    }


    // Simplifies function call by initializing an empty array to store the results.
    // PLEASE DO NOT CHANGE the implementation for this function as it will be used
    // to run the test cases.
    String[] prefixSearch(String s, int limit) {
        ArrayList<String> results = new ArrayList<String>();
        prefixSearch(s, results, limit);
        return results.toArray(new String[0]);
    }


    public static void main(String[] args) {

        Trie t = new Trie();
        t.insert("abbde");
        t.insert("abcd");
        t.insert("abcdef");
        t.insert("abed");
        t.insert("peck");
        t.insert("of");
        t.insert("pickled");
        t.insert("peppers");
        t.insert("pepppito");
        t.insert("pepi");
        t.insert("pik");



        String[] result1 = t.prefixSearch("ab.d", 5);
        String[] result2 = t.prefixSearch("pe.", 10);
        for(int i = 0; i < result1.length; i++) {
            System.out.println(result1[i]);
        }
        // result1 should be:
        // ["peck", "pepi", "peppers", "pepppito", "peter"]
        // result2 should contain the same elements with result1 but may be ordered arbitrarily
    }
}
