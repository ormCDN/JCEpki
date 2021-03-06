package com.algo.code.dijkstra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @program: algo
 * @description:
 * @author: liyy
 * @create: 2018-11-08 11:03
 **/
public class DijkstraAlgothrim {

    /**
     * 计算节点中“最便宜”的节点
     * @param costs
     * @param hasHandleList
     * @return
     */
    private String  getMiniCostKey(HashMap<String,Integer> costs, List<String> hasHandleList) {
        int mini = Integer.MAX_VALUE;
        String miniKey = null;
        for (String key : costs.keySet()) {
            if (!hasHandleList.contains(key)) {
                int cost = costs.get(key);
                if (mini > cost) {
                    mini = cost;
                    miniKey = key;
                }
            }
        }
        return miniKey;

    }

    /**
     * 狄克斯特拉算法实现
     * @param startKey
     * @param target
     * @param all
     */
    private void handle(String startKey,String target,HashMap<String,HashMap<String,Integer>> all) {
        //存放到各个节点所需要消耗的时间
        HashMap<String,Integer> costMap = new HashMap<String,Integer>();
        //到各个节点对应的父节点
        HashMap<String,String> parentMap = new HashMap<String,String>();
        //存放已处理过的节点key,已处理过的不重复处理
        List<String> hasHandleList = new ArrayList<String>();

        //首先获取开始节点相邻节点信息
        HashMap<String,Integer> start = all.get(startKey);

        //添加起点到各个相邻节点所需耗费的时间等信息
        for(String key:start.keySet()) {
            int cost = start.get(key);
            costMap.put(key, cost);
            parentMap.put(key,startKey);
        }


        //选择最"便宜"的节点，这边即耗费时间最低的
        String minCostKey = getMiniCostKey(costMap,hasHandleList);
        while( minCostKey!=null ) {
            System.out.print("处理节点："+minCostKey);
            HashMap<String,Integer> nodeMap = all.get(minCostKey);
            if (nodeMap!=null) {
                //该节点没有子节点可以处理了，末端节点
                handleNode(minCostKey,nodeMap,costMap,parentMap);
            }
            //添加该节点到已处理结束的列表中
            hasHandleList.add(minCostKey);
            //再次获取下一个最便宜的节点
            minCostKey = getMiniCostKey(costMap,hasHandleList);
        }
        if(parentMap.containsKey(target)) {
            System.out.print("到目标节点"+target+"最低耗费:"+costMap.get(target));
            List<String> pathList = new ArrayList<String>();
            String parentKey = parentMap.get(target);
            while (parentKey!=null) {
                pathList.add(0, parentKey);
                parentKey = parentMap.get(parentKey);
            }
            pathList.add(target);
            String path="";
            for(String key:pathList) {
                path = path + key + " --> ";
            }
            System.out.print("路线为"+path);
        } else {
            System.out.print("不存在到达"+target+"的路径");
        }
    }

    /**
     * 处理没有子节点的末端节点
     * @param startKey
     * @param nodeMap
     * @param costMap
     * @param parentMap
     */
    private void handleNode(String startKey,HashMap<String,Integer> nodeMap,HashMap<String,Integer> costMap,HashMap<String,String> parentMap) {

        for(String key : nodeMap.keySet()) {
            //获取原本到父节点所需要花费的时间
            int hasCost = costMap.get(startKey);
            //获取父节点到子节点所需要花费的时间
            int cost = nodeMap.get(key);
            //计算从最初的起点到该节点所需花费的总时间
            cost = hasCost + cost;

            if (!costMap.containsKey(key)) {
                //如果原本并没有计算过其它节点到该节点的花费
                costMap.put(key,cost);
                parentMap.put(key,startKey);
            }else {
                //获取原本耗费的时间
                int oldCost = costMap.get(key);
                if (cost < oldCost) {
                    //新方案到该节点耗费的时间更少
                    //更新到达该节点的父节点和消费时间对应的散列表
                    costMap.put(key,cost);
                    parentMap.put(key,startKey);
                    System.out.print("更新节点："+key + ",cost:" +oldCost + " --> " + cost);
                }
            }
        }
    }

}
