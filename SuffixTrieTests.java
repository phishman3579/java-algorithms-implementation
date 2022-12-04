@Test
public void testSuffixTrie() {
        String bookkeeper = "bookkeeper";
        SuffixTrie<String> trie = new SuffixTrie<String>(bookkeeper);
        assertTrue(SuffixTreeTest.suffixTreeTest(trie, bookkeeper));
        }

@Test
public void testSuffixTrieDoesSubStringExist() {
        SuffixTrie<String> trie = new SuffixTrie<String>("bookkeeper");
        assertTrue(trie.doesSubStringExist("keeper"));
        }

@Test
public void testSuffixTrieAdd() {
        SuffixTrie<String> trie = new SuffixTrie<String>("book");
        trie.add("keeper");
        assertTrue(SuffixTreeTest.suffixTreeTest(trie, "bookkeeper"));
        }

@Test
public void testSuffixTrieGetSuffixes() {
        SuffixTrie<String> trie = new SuffixTrie<String>("bookkeeper");
        Set<String> trieSuffixes = trie.getSuffixes();
        for(String suffix: trieSuffixes) {
        assertTrue(SuffixTreeTest.suffixTreeTest(trie, suffix));
        }
        }

@Test
public void testSuffixTrieToString() {
        SuffixTrie<String> trie = new SuffixTrie<String>("bookkeeper");
        String bookkeeper = trie.toString();
        assertTrue(SuffixTreeTest.suffixTreeTest(trie, bookkeeper));
        }