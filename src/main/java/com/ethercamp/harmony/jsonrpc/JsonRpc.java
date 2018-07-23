/*
 * Copyright 2015, 2016 Ether.Camp Inc. (US)
 * This file is part of Ethereum Harmony.
 *
 * Ethereum Harmony is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Ethereum Harmony is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Ethereum Harmony.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.ethercamp.harmony.jsonrpc;

import com.ethercamp.harmony.config.RpcEnabledCondition;
import com.ethercamp.harmony.util.AppConst;
import com.googlecode.jsonrpc4j.JsonRpcService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Value;
import org.ethereum.core.Block;
import org.ethereum.core.CallTransaction;
import org.ethereum.core.Transaction;
import org.ethereum.vm.LogInfo;
import org.springframework.context.annotation.Conditional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.ethercamp.harmony.jsonrpc.TypeConverter.toJsonHex;

/**
 * Created by Anton Nashatyrev on 25.11.2015.
 */
@JsonRpcService(AppConst.JSON_RPC_PATH)
@Conditional(RpcEnabledCondition.class)
public interface JsonRpc {

    @Value
    @AllArgsConstructor
    @ToString
    class SyncingResult {
        private final String startingBlock;
        private final String currentBlock;
        private final String highestBlock;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    class CallArguments {
        public String from;
        public String to;
        public String gas;
        public String gasPrice;
        public String value;
        public String data; // compiledCode
        public String nonce;

        @Override
        public String toString() {
            return "CallArguments{" +
                    "from='" + from + '\'' +
                    ", to='" + to + '\'' +
                    ", gas='" + gas + '\'' +
                    ", gasPrice='" + gasPrice + '\'' +
                    ", value='" + value + '\'' +
                    ", data='" + data + '\'' +
                    ", nonce='" + nonce + '\'' +
                    '}';
        }
    }

    class BlockResult {
        public String number; // QUANTITY - the block number. null when its pending block.
        public String hash; // DATA, 32 Bytes - hash of the block. null when its pending block.
        public String parentHash; // DATA, 32 Bytes - hash of the parent block.
        public String nonce; // DATA, 8 Bytes - hash of the generated proof-of-work. null when its pending block.
        public String sha3Uncles; // DATA, 32 Bytes - SHA3 of the uncles data in the block.
        public String logsBloom; // DATA, 256 Bytes - the bloom filter for the logs of the block. null when its pending block.
        public String transactionsRoot; // DATA, 32 Bytes - the root of the transaction trie of the block.
        public String stateRoot; // DATA, 32 Bytes - the root of the final state trie of the block.
        public String receiptsRoot; // DATA, 32 Bytes - the root of the receipts trie of the block.
        public String miner; // DATA, 20 Bytes - the address of the beneficiary to whom the mining rewards were given.
        public String difficulty; // QUANTITY - integer of the difficulty for this block.
        public String totalDifficulty; // QUANTITY - integer of the total difficulty of the chain until this block.
        public String extraData; // DATA - the "extra data" field of this block
        public String size;//QUANTITY - integer the size of this block in bytes.
        public String gasLimit;//: QUANTITY - the maximum gas allowed in this block.
        public String gasUsed; // QUANTITY - the total used gas by all transactions in this block.
        public String timestamp; //: QUANTITY - the unix timestamp for when the block was collated.
        public Object[] transactions; //: Array - Array of transaction objects, or 32 Bytes transaction hashes depending on the last given parameter.
        public String[] uncles; //: Array - Array of uncle hashes.

        @Override
        public String toString() {
            return "BlockResult{" +
                    "number='" + number + '\'' +
                    ", hash='" + hash + '\'' +
                    ", parentHash='" + parentHash + '\'' +
                    ", nonce='" + nonce + '\'' +
                    ", sha3Uncles='" + sha3Uncles + '\'' +
                    ", logsBloom='" + logsBloom + '\'' +
                    ", transactionsRoot='" + transactionsRoot + '\'' +
                    ", stateRoot='" + stateRoot + '\'' +
                    ", receiptsRoot='" + receiptsRoot + '\'' +
                    ", miner='" + miner + '\'' +
                    ", difficulty='" + difficulty + '\'' +
                    ", totalDifficulty='" + totalDifficulty + '\'' +
                    ", extraData='" + extraData + '\'' +
                    ", size='" + size + '\'' +
                    ", gas='" + gasLimit + '\'' +
                    ", gasUsed='" + gasUsed + '\'' +
                    ", timestamp='" + timestamp + '\'' +
                    ", transactions=" + Arrays.toString(transactions) +
                    ", uncles=" + Arrays.toString(uncles) +
                    '}';
        }
    }

    class CompilationResult {
        public String code;
        public CompilationInfo info;

        @Override
        public String toString() {
            return "CompilationResult{" +
                    "code='" + code + '\'' +
                    ", info=" + info +
                    '}';
        }
    }

    class CompilationInfo {
        public String source;
        public String language;
        public String languageVersion;
        public String compilerVersion;
        public CallTransaction.Function[] abiDefinition;
        public String userDoc;
        public String developerDoc;

        @Override
        public String toString() {
            return "CompilationInfo{" +
                    "source='" + source + '\'' +
                    ", language='" + language + '\'' +
                    ", languageVersion='" + languageVersion + '\'' +
                    ", compilerVersion='" + compilerVersion + '\'' +
                    ", abiDefinition=" + abiDefinition +
                    ", userDoc='" + userDoc + '\'' +
                    ", developerDoc='" + developerDoc + '\'' +
                    '}';
        }
    }

    class FilterRequest {
        public String fromBlock;
        public String toBlock;
        public Object address;
        public Object[] topics;
        public String blockHash;  // EIP-234: makes fromBlock = toBlock = blockHash

        @Override
        public String toString() {
            return "FilterRequest{" +
                    "fromBlock='" + fromBlock + '\'' +
                    ", toBlock='" + toBlock + '\'' +
                    ", address=" + address +
                    ", topics=" + Arrays.toString(topics) +
                    ", blockHash='" + blockHash + '\'' +
                    '}';
        }
    }

    class LogFilterElement {
        public String logIndex;
        public String transactionIndex;
        public String transactionHash;
        public String blockHash;
        public String blockNumber;
        public String address;
        public String data;
        public String[] topics;

        public LogFilterElement(LogInfo logInfo, Block b, Integer txIndex, Transaction tx, int logIdx) {
            logIndex = toJsonHex(logIdx);
            blockNumber = b == null ? null : toJsonHex(b.getNumber());
            blockHash = b == null ? null : toJsonHex(b.getHash());
            transactionIndex = b == null ? null : toJsonHex(txIndex);
            transactionHash = toJsonHex(tx.getHash());
            address = toJsonHex(tx.getReceiveAddress());
            data = toJsonHex(logInfo.getData());
            topics = new String[logInfo.getTopics().size()];
            for (int i = 0; i < topics.length; i++) {
                topics[i] = toJsonHex(logInfo.getTopics().get(i).getData());
            }
        }

        @Override
        public String toString() {
            return "LogFilterElement{" +
                    "logIndex='" + logIndex + '\'' +
                    ", blockNumber='" + blockNumber + '\'' +
                    ", blockHash='" + blockHash + '\'' +
                    ", transactionHash='" + transactionHash + '\'' +
                    ", transactionIndex='" + transactionIndex + '\'' +
                    ", address='" + address + '\'' +
                    ", data='" + data + '\'' +
                    ", topics=" + Arrays.toString(topics) +
                    '}';
        }
    }

    String web3_clientVersion();
    String web3_sha3(String data) throws Exception;
    String net_version();
    String net_peerCount();
    boolean net_listening();
    String eth_protocolVersion();
    Object eth_syncing();
    String eth_coinbase();
    boolean eth_mining();
    String eth_hashrate();
    String eth_gasPrice();
    String[] eth_accounts();
    String eth_blockNumber();
    String eth_getBalance(String address, String block) throws Exception;
    String eth_getLastBalance(String address) throws Exception;

    String eth_getStorageAt(String address, String storageIdx, String blockId) throws Exception;

    String eth_getTransactionCount(String address, String blockId) throws Exception;

    String eth_getBlockTransactionCountByHash(String blockHash)throws Exception;
    String eth_getBlockTransactionCountByNumber(String bnOrId)throws Exception;
    String eth_getUncleCountByBlockHash(String blockHash)throws Exception;
    String eth_getUncleCountByBlockNumber(String bnOrId)throws Exception;
    String eth_getCode(String addr, String bnOrId)throws Exception;
    String eth_sign(String addr, String data) throws Exception;
    String eth_sendTransaction(CallArguments transactionArgs) throws Exception;
    String eth_sendRawTransaction(String rawData) throws Exception;
    String eth_call(CallArguments args, String bnOrId) throws Exception;
    String eth_estimateGas(CallArguments args) throws Exception;
    BlockResult eth_getBlockByHash(String blockHash, Boolean fullTransactionObjects) throws Exception;
    BlockResult eth_getBlockByNumber(String bnOrId, Boolean fullTransactionObjects) throws Exception;
    TransactionResultDTO eth_getTransactionByHash(String transactionHash) throws Exception;
    TransactionResultDTO eth_getTransactionByBlockHashAndIndex(String blockHash, String index) throws Exception;
    TransactionResultDTO eth_getTransactionByBlockNumberAndIndex(String bnOrId, String index) throws Exception;
    TransactionReceiptDTO eth_getTransactionReceipt(String transactionHash) throws Exception;

    TransactionReceiptDTOExt ethj_getTransactionReceipt(String transactionHash) throws Exception;

    BlockResult eth_getUncleByBlockHashAndIndex(String blockHash, String uncleIdx) throws Exception;

    BlockResult eth_getUncleByBlockNumberAndIndex(String blockId, String uncleIdx) throws Exception;

    String[] eth_getCompilers();
    CompilationResult eth_compileSolidity(String contract) throws Exception;
    CompilationResult eth_compileLLL(String contract);
    CompilationResult eth_compileSerpent(String contract);

//    String eth_resend();
//    String eth_pendingTransactions();

    String eth_newFilter(FilterRequest fr) throws Exception;

//    String eth_newFilter(String fromBlock, String toBlock, String address, String[] topics) throws Exception;

    String eth_newBlockFilter();
    String eth_newPendingTransactionFilter();
    boolean eth_uninstallFilter(String id);
    Object[] eth_getFilterChanges(String id);

    Object[] eth_getFilterLogs(String id);

    Object[] eth_getLogs(FilterRequest fr) throws Exception;

    List<Object> eth_getWork();
    boolean eth_submitWork(String nonce, String header, String digest) throws Exception;
    boolean eth_submitHashrate(String hashrate, String id);
    String shh_version();
//    String shh_post();
//    String shh_newIdentity();
//    String shh_hasIdentity();
//    String shh_newGroup();
//    String shh_addToGroup();
//    String shh_newFilter();
//    String shh_uninstallFilter();
//    String shh_getFilterChanges();
//    String shh_getMessages();
//
//
    boolean admin_addPeer(String enode);
//
//    String admin_exportChain();
//    String admin_importChain();
//    String admin_sleepBlocks();
//    String admin_verbosity();
//    String admin_setSolc();
//    String admin_startRPC();
//    String admin_stopRPC();
//    String admin_setGlobalRegistrar();
//    String admin_setHashReg();
//    String admin_setUrlHint();
//    String admin_saveInfo();
//    String admin_register();
//    String admin_registerUrl();
//    String admin_startNatSpec();
//    String admin_stopNatSpec();
//    String admin_getContractInfo();
//    String admin_httpGet();
    Map<String, ?> admin_nodeInfo() throws Exception;
    List<Map<String, ?>> admin_peers();
//    String admin_datadir();
//    String net_addPeer();

    boolean miner_start();
    boolean miner_stop();
    boolean miner_setEtherbase(String coinBase) throws Exception;
    boolean miner_setExtra(String data) throws Exception;
    boolean miner_setGasPrice(String newMinGasPrice);
//    boolean miner_startAutoDAG();
//    boolean miner_stopAutoDAG();
//    boolean miner_makeDAG();
//    String miner_hashrate();

//    String debug_printBlock();
//    String debug_getBlockRlp();
//    String debug_setHead();
//    String debug_processBlock();
//    String debug_seedHash();
//    String debug_dumpBlock();
//    String debug_metrics();

    String personal_newAccount(String seed);

    String personal_importRawKey(String keydata, String passphrase);

    boolean personal_unlockAccount(String addr, String pass, String duration);
    boolean personal_lockAccount(String address);

    String[] personal_listAccounts();
    String[] ethj_listAvailableMethods();
    String personal_signAndSendTransaction(CallArguments tx, String password);
}
