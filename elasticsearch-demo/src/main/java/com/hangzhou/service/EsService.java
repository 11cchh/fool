package com.hangzhou.service;

import com.hangzhou.pojo.Hotel;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author Faye
 * @Date 2023/1/2 16:59
 */
@Slf4j
@Service
public class EsService {
    @Autowired
    RestHighLevelClient client;

    /**
     * 插入单条文档
     *
     * @param indexName 索引名称
     * @param docId     文档 id
     * @param dataMap   文档数据
     */
    public void singleIndexDoc(String indexName, String docId, Map<String, Object> dataMap) {
        IndexRequest indexRequest = new IndexRequest(indexName).id(docId).source(dataMap);
        try {
            IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
            String index = indexResponse.getIndex();
            String id = indexResponse.getId();
            long version = indexResponse.getVersion();
            log.info("index = " + index + ", id = " + id + ", version = " + version);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 批量添加文档
     *
     * @param indexName  索引名称
     * @param docIdKey   文档 id
     * @param recordList 文档数据
     */
    public void bulkIndexDoc(String indexName, String docIdKey, List<Map<String, Object>> recordList) {
        BulkRequest bulkRequest = new BulkRequest(indexName);
        recordList.forEach(e -> {
            String docId = e.get(docIdKey).toString();
            IndexRequest indexRequest = new IndexRequest().id(docId).source(e);
            bulkRequest.add(indexRequest);
        });
        // 设置超时时间
        bulkRequest.timeout(TimeValue.timeValueSeconds(5));
        try {
            // 批量提交
            BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);
            if (bulkResponse.hasFailures()) {
                log.info("bulk fail: " + bulkResponse.buildFailureMessage());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 更新单条文档
     *
     * @param indexName 索引名称
     * @param docId     文档 id
     * @param dataMap   文档数据
     */
    public void singleUpdate(String indexName, String docId, Map<String, Object> dataMap) {
        UpdateRequest updateRequest = new UpdateRequest(indexName, docId);
        updateRequest.doc(dataMap);
        try {
            UpdateResponse updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
            String index = updateResponse.getIndex();
            String id = updateResponse.getId();
            long version = updateResponse.getVersion();
            log.info("index = " + index + ", id = " + id + ", version = " + version);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 单条文档删除
     *
     * @param index 索引
     * @param docId 文档 id
     */
    public void singleDelete(String index, String docId) {
        DeleteRequest deleteRequest = new DeleteRequest(index, docId);
        try {
            DeleteResponse deleteResponse = client.delete(deleteRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Hotel> getHotelByTitle(String keyword) {
        SearchRequest searchRequest = new SearchRequest("hotel");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 构建 query
        searchSourceBuilder.query(QueryBuilders.matchQuery("title", keyword));
        searchRequest.source(searchSourceBuilder);

        List<Hotel> results = new ArrayList<>();
        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            RestStatus status = searchResponse.status();
            if (RestStatus.OK != status) {
                return results;
            }
            SearchHits hits = searchResponse.getHits();
            hits.forEach(e -> {
                Hotel hotel = new Hotel();
                hotel.setId(e.getId());
                hotel.setIndex(e.getIndex());
                hotel.setScore(e.getScore());

                Map<String, Object> dataMap = e.getSourceAsMap();
                hotel.setTitle((String) dataMap.get("title"));
                hotel.setCity((String) dataMap.get("city"));
                hotel.setPrice((Integer) dataMap.get("price"));
                results.add(hotel);
            });
            return results;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
