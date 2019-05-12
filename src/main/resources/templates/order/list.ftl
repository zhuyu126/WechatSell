<#--<h1>${orderDTOPage.getContent()}</h1>-->
<html>
    <head>
        <meta charset="utf-8">
        <title>卖家商品列表</title>
        <link href="https://cdn.bootcss.com/twitter-bootstrap/3.0.1/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
    <div class="container">
        <div class="row clearfix">
            <div class="col-md-12 column">
                <table class="table table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>订单id</th>
                        <th>姓名</th>
                        <th>手机号</th>
                        <th>地址</th>
                        <th>金额</th>
                        <th>订单状态</th>
                        <#--<th>支付方式</th>-->
                        <th>支付状态</th>
                        <th>创建时间</th>
                        <th colspan="2">操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list orderDTOPage.content as orderDTO>
                    <tr>
                        <td>${orderDTO.orderId}</td>
                        <td>${orderDTO.buyerName}</td>
                        <td>${orderDTO.buyerPhone}</td>
                        <td>${orderDTO.buyerAddress}</td>
                        <td>${orderDTO.orderAmount}</td>
                        <td>${orderDTO.orderStatusEnum.msg}</td>
                        <td>${orderDTO.payStatusEnum.msg}</td>
                        <td>${orderDTO.createTime}</td>
                        <td><a href="#">详情</td>
                        <td>
                            <#if orderDTO.getOrderStatusEnum().msg!="已取消订单">
                                <a href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}">取消</a>
                            </#if>
                        </td>
                    </tr>
                    </#list>

                    </tbody>
                </table>
            </div>

            <#--分页-->
            <br/>
            <div class="col-md-12 column" >
                <ul class="pagination pull-right">
                    <#if currentPage lte 1>
                        <li class="disable"><a href="#">上一页</a></li>
                    <#else>
                        <li><a href="/sell/seller/order/list?page=${currentPage-1}&size=${size}">上一页</a></li>
                    </#if>
                    <#if orderDTOPage.getTotalPages() gte 10>
                        <li><a href="/sell/seller/order/list?page=1&size=${size}">1</a></li>
                        <li><a href="/sell/seller/order/list?page=2&size=${size}">2</a></li>
                        <li><a href="#">...</a></li>
                        <li><a href="/sell/seller/order/list?page=${orderDTOPage.getTotalPages()-1}&size=${size}">${orderDTOPage.getTotalPages()-1}</a></li>
                        <li><a href="/sell/seller/order/list?page=${orderDTOPage.getTotalPages()}&size=${size}">${orderDTOPage.getTotalPages()}</a></li>
                    <#else>
                        <#list 1..orderDTOPage.getTotalPages() as index>
                            <#if currentPage==index>
                        <li class="disabled"><a href="#"> ${index}</a></li>
                            <#else>
                         <li><a href="/sell/seller/order/list?page=${index}&size=${size}"> ${index}</a></li>
                            </#if>
                        </#list>
                    </#if>
                    <#--<#list 1..orderDTOPage.getTotalPages() as index>-->
                        <#--<#if currentPage==index>-->
                        <#--<li class="disabled"><a href="#"> ${index}</a></li>-->
                        <#--<#else>-->
                         <#--<li><a href="/sell/seller/order/list?page=${index}&size=${size}"> ${index}</a></li>-->
                        <#--</#if>-->
                    <#--</#list>-->
                    <#if currentPage gte orderDTOPage.getTotalPages()>
                        <li class="disabled"><a href="#">下一页</a>
                    <#else>
                        <li><a href="/sell/seller/order/list?page=${currentPage+1}&size=${size}">下一页</a>
                    </#if>
                    </li>
                </ul>
            </div>
        </div>
    </div>
    </body>
</html>
