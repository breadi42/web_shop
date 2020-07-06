package com.applepieme.controller;

import com.applepieme.bean.Cart;
import com.applepieme.bean.Goods;
import com.applepieme.bean.User;
import com.applepieme.service.FactoryService;
import com.applepieme.service.GoodsService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品Controller
 * 处理商品相关请求
 *
 * @author applepieme@yeah.net
 * @date 2020/6/30 14:53
 */
@WebServlet("*.goods")
public class GoodsController extends HttpServlet {
    /**
     * 商品服务层实现类对象
     */
    GoodsService goodsService = FactoryService.getService(GoodsService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取请求路径
        String methodName = req.getServletPath();
        // 从请求路径获取方法名
        methodName = methodName.substring(1, methodName.length() - 6);
        try {
            // 获取封装了前面方法名对应方法的Method对象
            Method method = this.getClass().getDeclaredMethod(methodName, HttpServletRequest.class,
                    HttpServletResponse.class);
            // 传递实例对象，调用对应方法
            method.invoke(this, req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取用户主页的推荐商品
     *
     * @param req  HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException ServletException
     * @throws IOException      IOException
     */
    private void initIndex(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取所有商品
        List<Goods> goodsList = goodsService.listGoods();
        if (goodsList != null) {
            // 调用getHotGoods方法，并把推荐商品存放到request中
            req.setAttribute("hotGoods", getHotGoods(goodsList));
            // 转发到用户主页
            req.getRequestDispatcher("WEB-INF/front/welcome.jsp").forward(req, resp);
        }
    }

    /**
     * 用户查询所有商品
     *
     * @param req  HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException ServletException
     * @throws IOException      IOException
     */
    private void queryGoods(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 获取所有商品
        List<Goods> goodsList = goodsService.listGoods();
        if (goodsList != null) {
            // 把商品类型存放到request中
            req.setAttribute("type", "Goods");
            // 调用getHotGoods方法，并把推荐商品存放到request中
            req.setAttribute("hotGoods", getHotGoods(goodsList));
            // 如果商品数量超过8个
            if (goodsList.size() > 8) {
                // 设置分页
                setPageGoods(req, resp, goodsList);
            } else {
                // 否则直接显示
                req.setAttribute("goodsList", goodsList);
            }
            // 请求转发到商品页面
            req.getRequestDispatcher("WEB-INF/front/goods.jsp").forward(req, resp);
        }
    }

    /**
     * 查询电子产品
     *
     * @param req  HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException ServletException
     * @throws IOException      IOException
     */
    private void queryElectronic(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 根据商品分类查询到所有的电子产品
        List<Goods> electronicList = goodsService.listGoodsByType("电子产品");
        if (electronicList != null) {
            // 把商品分类存放到request中
            req.setAttribute("type", "Electronic");
            // 调用getHotGoods方法，并把电子产品中的推荐商品存放到request中
            req.setAttribute("hotGoods", getHotGoods(electronicList));
            // 如果商品数量大于8个
            if (electronicList.size() > 8) {
                // 则进行分页显示
                setPageGoods(req, resp, electronicList);
            } else {
                // 否则直接显示
                req.setAttribute("goodsList", electronicList);
            }
            // 转发到商品页面
            req.getRequestDispatcher("WEB-INF/front/goods.jsp").forward(req, resp);
        }
    }

    /**
     * 查询服装
     *
     * @param req  HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException ServletException
     * @throws IOException      IOException
     */
    private void queryClothing(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 根据商品分类获取所有的服装
        List<Goods> clothingList = goodsService.listGoodsByType("服装");
        if (clothingList != null) {
            // 把商品类型存放到request中
            req.setAttribute("type", "Clothing");
            // 调用getHotGoods方法，获取服装中的推荐商品
            req.setAttribute("hotGoods", getHotGoods(clothingList));
            // 如果商品数量大于8个
            if (clothingList.size() > 8) {
                // 则分页显示
                setPageGoods(req, resp, clothingList);
            } else {
                // 否则直接显示
                req.setAttribute("goodsList", clothingList);
            }
            // 转发到商品页面
            req.getRequestDispatcher("WEB-INF/front/goods.jsp").forward(req, resp);
        }
    }

    /**
     * 查询食品
     *
     * @param req  HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException ServletException
     * @throws IOException      IOException
     */
    private void queryFood(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 根据商品分类查询所有食品
        List<Goods> foodList = goodsService.listGoodsByType("食品");
        if (foodList != null) {
            // 把商品类型存放到request中
            req.setAttribute("type", "Food");
            // 调用getHotGoods方法，获取食品中的推荐商品
            req.setAttribute("hotGoods", getHotGoods(foodList));
            // 如果商品数量大于8个
            if (foodList.size() > 8) {
                // 则分页显示
                setPageGoods(req, resp, foodList);
            } else {
                // 否则直接显示
                req.setAttribute("goodsList", foodList);
            }
            // 转发到商品页面
            req.getRequestDispatcher("WEB-INF/front/goods.jsp").forward(req, resp);
        }
    }

    /**
     * 根据关键字查询
     *
     * @param req  HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException ServletException
     * @throws IOException      IOException
     */
    private void queryByKey(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 根据输入框输入的关键字获取相关商品列表
        List<Goods> keyList = goodsService.listGoodsByKey(req.getParameter("key"));
        if (keyList != null) {
            // 调用getHotGoods方法，获取相关商品中的推荐商品
            req.setAttribute("hotGoods", getHotGoods(keyList));
            // 如果商品数量大于8个
            if (keyList.size() > 8) {
                // 则分页显示
                setPageGoods(req, resp, keyList);
            } else {
                // 否则直接显示
                req.setAttribute("goodsList", keyList);
            }
            // 转发到商品页面
            req.getRequestDispatcher("WEB-INF/front/goods.jsp").forward(req, resp);
        }
    }

    /**
     * 查看商品详情
     *
     * @param req  HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException ServletException
     * @throws IOException      IOException
     */
    private void details(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取所有商品
        List<Goods> goodsList = goodsService.listGoods();
        // 获取所要查看的商品id
        int id = Integer.parseInt(req.getParameter("id"));
        // 获取所要查看的商品
        Goods goods = goodsService.getGoodsById(id);
        if (goods != null && goodsList != null) {
            // 调用getHotGoods方法，获取推荐商品
            req.setAttribute("hotGoods", getHotGoods(goodsList));
            // 把要查看的商品存放到request中
            req.setAttribute("goods", goods);
        }
        // 转发到商品详情页面
        req.getRequestDispatcher("WEB-INF/front/details.jsp").forward(req, resp);
    }

    /**
     * 商品加入购物车
     *
     * @param req  HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException ServletException
     * @throws IOException      IOException
     */
    private void addCart(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置响应类型为application/json
        resp.setContentType("application/json");
        HttpSession session = req.getSession();
        PrintWriter out = resp.getWriter();
        // 获取要加入购物车的商品数量
        int number = Integer.parseInt(req.getParameter("number"));
        // 获取要加入购物车的商品id
        int id = Integer.parseInt(req.getParameter("id"));
        // 根据商品id获取到该商品
        Goods goods = goodsService.getGoodsById(id);
        // 初始化购物车中该商品的数量
        goods.setNumber(number);
        // 获取购物车对象
        Cart cart = (Cart) session.getAttribute("userCart");
        // 若购物车对象为空，则说明未登录，返回状态码300
        if (cart == null) {
            out.println(300);
            return;
        }
        // 获取购物车中的商品列表
        List<Goods> cartGoodsList = cart.getCartGoodsList();
        // 若商品列表为空，说明购物车中还没有商品
        if (cartGoodsList == null) {
            // 创建一个ArrayList对象
            cartGoodsList = new ArrayList<>();
            // 把商品添加到购物车的商品列表中
            cartGoodsList.add(goods);
            // 初始化购物车的商品列表
            cart.setCartGoodsList(cartGoodsList);
            // 更新session中的购物车对象
            session.setAttribute("userCart", cart);
            // 加入成功，返回状态码200
            out.println(200);
            return;
        }
        // 如果商品列表不为空，则查找购物车中相同的商品
        for (Goods item : cartGoodsList) {
            if (item.getGoodsId() == id) {
                // 找到后相应的商品数量增加
                item.setNumber(item.getNumber() + number);
                // 更新session中的购物车对象
                session.setAttribute("userCart", cart);
                // 加入成功，返回200
                out.println(200);
                return;
            }
        }
        // 若购物车不为空，加入的又是新商品，直接把商品添加到商品列表
        cartGoodsList.add(goods);
        // 更新session中的购物车对象
        session.setAttribute("userCart", cart);
        // 加入成功，返回200
        out.println(200);
    }

    /**
     * 从购物车中移除商品
     *
     * @param req  HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException ServletException
     * @throws IOException      IOException
     */
    private void removeCartGoods(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 把响应类型设为application/json
        resp.setContentType("application/json");
        // 获取session对象
        HttpSession session = req.getSession();
        // 获取要移除的商品id
        int id = Integer.parseInt(req.getParameter("id"));
        // 获取购物车对象
        Cart userCart = (Cart) session.getAttribute("userCart");
        // 获取购物车中的商品列表
        List<Goods> goodsList = userCart.getCartGoodsList();
        // 找到要删除商品，从列表中移除
        goodsList.removeIf(item -> item.getGoodsId() == id);
        if (goodsList.isEmpty()) {
            // 如果列表已经没有商品，则把列表设为空
            userCart.setCartGoodsList(null);
        }
        // 更新session中的购物车对象
        session.setAttribute("userCart", userCart);
        // 移除成功，返回状态码200
        resp.getWriter().println(200);
    }

    /**
     * 购买商品时的订单详情
     *
     * @param req  HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException ServletException
     * @throws IOException      IOException
     */
    private void orderDetails(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取要购买的商品id
        int id = Integer.parseInt(req.getParameter("id"));
        // 获取要购买的数量
        int num = Integer.parseInt(req.getParameter("num"));
        // 获取到要购买的商品
        Goods goods = goodsService.getGoodsById(id);
        // 初始化商品的数量
        goods.setNumber(num);
        // 获取购物车对象
        Cart userCart = (Cart) req.getSession().getAttribute("userCart");
        // 获取当前用户对象
        User user = userCart.getUser();
        // 把要购买的商品对象保存到request中
        req.setAttribute("goods", goods);
        // 把当前用户对象保存到request中
        req.setAttribute("user", user);
        // 转发到订单详情页面
        req.getRequestDispatcher("WEB-INF/front/orderDetails.jsp").forward(req, resp);
    }

    /**
     * 查询所有商品
     *
     * @param req  HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException ServletException
     * @throws IOException      IOException
     */
    private void listGoods(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取所有商品
        List<Goods> goodsList = goodsService.listGoods();
        if (goodsList != null) {
            // 如果商品数量大于6个，则分页显示
            if (goodsList.size() > 6) {
                // 获取当前所在页
                int page = Integer.parseInt(req.getParameter("page"));
                // 获取总页数
                int total = (goodsList.size() - 1) / 6 + 1;
                // 页码数组
                int[] array = new int[999];
                // 初始化页码数组
                for (int i = 0; i < total; i++) {
                    array[i] = i + 1;
                }
                // 当前页的商品列表
                List<Goods> pageGoods = new ArrayList<>();
                // 是第几页就循环几次
                for (int i = 0; i < page; i++) {
                    // 每次翻页清空当前页的商品列表
                    pageGoods.clear();
                    // 每页6个商品
                    for (int j = 0; j < 6; j++) {
                        // 商品的索引等于商品列表的大小时，商品已经遍历完
                        if ((j + (page - 1) * 6) == goodsList.size()) {
                            break;
                        }
                        // 把商品放入当前页的商品列表中
                        pageGoods.add(goodsList.get(j + (page - 1) * 6));
                    }
                }
                // 把当前页码，总页数，页码数组，当前页商品列表存放到request中
                req.setAttribute("page", page);
                req.setAttribute("total", total);
                req.setAttribute("array", array);
                req.setAttribute("goodsList", pageGoods);
            } else {
                // 商品数量小于等于6个时，直接显示
                req.setAttribute("goodsList", goodsList);
            }
        }
        // 转发到商品管理页面
        req.getRequestDispatcher("manage_goods.page").forward(req, resp);
    }

    /**
     * 展示商品信息
     *
     * @param req  HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException ServletException
     * @throws IOException      IOException
     */
    private void showGoods(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取要查看的商品id
        int id = Integer.parseInt(req.getParameter("id"));
        // 根据id获取要查看的商品对象
        Goods goods = goodsService.getGoodsById(id);
        if (goods != null) {
            // 把商品对象存放到request中
            req.setAttribute("goods", goods);
        }
        // 转发到修改商品信息页面
        req.getRequestDispatcher("manage_updateGoods.page").forward(req, resp);
    }

    /**
     * 更新商品信息
     *
     * @param req  HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException ServletException
     * @throws IOException      IOException
     */
    private void updateGoods(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 响应类型设为application/json
        resp.setContentType("application/json");
        // 创建一个商品对象
        Goods goods = new Goods();
        // 用修改后的信息初始化它的属性
        goods.setGoodsId(Integer.parseInt(req.getParameter("goodsId")));
        goods.setGoodsName(req.getParameter("goodsName"));
        goods.setType(req.getParameter("type"));
        goods.setPrice(Double.parseDouble(req.getParameter("price")));
        goods.setDetails(req.getParameter("details"));
        goods.setStock(Integer.parseInt(req.getParameter("stock")));
        // 调用服务层更新商品信息，若影响行数大于等于1，说明修改成功
        if (goodsService.updateGoods(goods) >= 1) {
            // 成功，返回200
            resp.getWriter().println(200);
        } else {
            // 失败，返回400
            resp.getWriter().println(400);
        }
    }

    /**
     * 删除商品
     *
     * @param req  HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException ServletException
     * @throws IOException      IOException
     */
    private void deleteGoods(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 把响应类型设为application/json
        resp.setContentType("application/json");
        // 获取要删除商品的id
        int id = Integer.parseInt(req.getParameter("id"));
        // 根据id获取商品
        Goods goods = goodsService.getGoodsById(id);
        // 获取要删除的商品的图片名
        String image = goods.getImage();
        // 调用服务层删除商品，若变化行数大于等于1，说明删除成功
        if (goodsService.deleteGoods(id) >= 1) {
            // 商品的图片存放路径
            String outPath = req.getSession().getServletContext().getRealPath("/static/img/goods/");
            // 创建File对象
            File file = new File(outPath + image);
            // 删除图片
            if (file.delete()) {
                // 成功，返回200
                resp.getWriter().println(200);
            } else {
                // 数据库中已经删除成功，但删除图片失败，返回300
                resp.getWriter().println(300);
            }
        } else {
            // 失败，返回400
            resp.getWriter().println(400);
        }
    }

    /**
     * 根据当前商品分类获取对应推荐商品列表
     *
     * @param goodsList 商品分类对应的商品列表
     * @return List
     */
    private List<Goods> getHotGoods(List<Goods> goodsList) {
        // 如果当前商品列表数小于等于4个
        if (goodsList.size() <= 4) {
            // 直接返回所有商品为推荐商品
            return goodsList;
        }
        // 随机一个开始索引值，该值要小于当前商品列表的商品数量 - 4
        int start = (int) Math.floor(Math.random() * (goodsList.size() - 4));
        // 创建一个推荐商品列表
        List<Goods> hotGoods = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            // 从start开始，给列表添加4个商品
            hotGoods.add(goodsList.get(start + i));
        }
        // 返回推荐商品列表
        return hotGoods;
    }

    /**
     * 当商品数超过8个时，分页展示
     *
     * @param req       HttpServletRequest
     * @param resp      HttpServletResponse
     * @param goodsList 当前要显示的商品列表
     * @throws ServletException ServletException
     * @throws IOException      IOException
     */
    private void setPageGoods(HttpServletRequest req, HttpServletResponse resp, List<Goods> goodsList)
            throws ServletException, IOException {
        // 获取当前页码
        int page = Integer.parseInt(req.getParameter("page"));
        /* 计算总共多少页
         * 需要使用商品数 - 1 来除以每页个数再 + 1
         * + 1 是因为当商品数不是每页个数的整数倍时，由于是int类型，会向下取整，所以需要 + 1
         * 商品数需要 - 1 是因为如果商品个数恰好是每页个数的整数倍，那么在本身不需要分页的情况下，会多出1页空页 */
        int total = (goodsList.size() - 1) / 8 + 1;
        // 创建页码数组
        int[] array = new int[999];
        // 初始化页码数组
        for (int i = 0; i < total; i++) {
            array[i] = i + 1;
        }
        // 创建当前页的商品列表
        List<Goods> pageGoods = new ArrayList<>();
        // 当前是多少页，就循环多少次
        for (int i = 0; i < page; i++) {
            // 每次翻页清空当前页的商品列表
            pageGoods.clear();
            // 每页显示8个商品
            for (int j = 0; j < 8; j++) {
                // 如果索引值等于列表中的商品数，说明已经遍历完商品
                if ((j + (page - 1) * 8) == goodsList.size()) {
                    break;
                }
                // 把商品添加到当前页的商品列表中
                pageGoods.add(goodsList.get(j + (page - 1) * 8));
            }
        }
        // 把当前页码，总页数，页码数组，当前页商品列表存放到request中
        req.setAttribute("page", page + "");
        req.setAttribute("total", total);
        req.setAttribute("array", array);
        req.setAttribute("goodsList", pageGoods);
    }

    /**
     * 添加商品
     *
     * @param req  HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException ServletException
     * @throws IOException      IOException
     */
    private void addGoods(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置响应类型为application/json
        resp.setContentType("application/json");
        // 商品图片的存放路径
        String outPath = req.getSession().getServletContext().getRealPath("/static/img/goods");
        // 商品图片名为当前的时间戳
        String imageName = System.currentTimeMillis() + ".jpg";
        // 创建商品对象
        Goods goods = new Goods();
        // 若请求不是二进制流格式，则直接退出
        if (!ServletFileUpload.isMultipartContent(req)) {
            resp.getWriter().println(400);
            return;
        }
        // 创建FileItemFactory对象
        FileItemFactory factory = new DiskFileItemFactory();
        // 创建文件上传的处理对象
        ServletFileUpload upload = new ServletFileUpload(factory);
        try {
            // 解析二进制流格式的请求
            List<FileItem> items = upload.parseRequest(req);
            // 遍历每个FileItem
            for (FileItem item : items) {
                // 获取表单中的name
                String formName = item.getFieldName();
                // 如果是普通的表单
                if (item.isFormField()) {
                    // 使用switch给商品对象初始化
                    switch (formName) {
                        case "goodsName":
                            goods.setGoodsName(item.getString("utf-8"));
                            break;
                        case "type":
                            goods.setType(item.getString("utf-8"));
                            break;
                        case "details":
                            goods.setDetails(item.getString("utf-8"));
                            break;
                        case "price":
                            goods.setPrice(Double.parseDouble(item.getString("utf-8")));
                            break;
                        case "stock":
                            goods.setStock(Integer.parseInt(item.getString("utf-8")));
                            break;
                        default:
                    }
                } else {
                    // 判断文件是否为JPG类型
                    if (!"jpg".equals(item.getName().substring(item.getName().length() - 3))) {
                        // 不是则返回状态码300
                        resp.getWriter().println(300);
                        return;
                    }
                    // 给商品对象初始化图片名
                    goods.setImage(imageName);
                    try {
                        // 把上传的图片保存到对应的路径中
                        item.write(new File(outPath, imageName));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            // 调用服务层添加商品，如果改变行数大于0，说明添加成功
            if (goodsService.addGoods(goods) >= 1) {
                // 成功，返回200
                resp.getWriter().println(200);
                // 若添加失败，则把刚刚上传的图片删除
            } else {
                // 重新初始化图片路径
                outPath = req.getSession().getServletContext().getRealPath("/static/img/goods/");
                // 创建File对象
                File file = new File(outPath + imageName);
                // 删除图片
                if (file.delete()) {
                    // 失败返回400
                    resp.getWriter().println(400);
                }
            }
        } catch (FileUploadException e) {
            // 异常时返回400
            resp.getWriter().println(400);
            e.printStackTrace();
        }
    }

    /**
     * 修改商品库存
     *
     * @param req  HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException ServletException
     * @throws IOException      IOException
     */
    private void changeStock(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取要修改的商品id
        int id = Integer.parseInt(req.getParameter("goodsId"));
        // 获取当前订单的商品数
        int number = Integer.parseInt(req.getParameter("number"));
        // 获取要修改的商品
        Goods goods = goodsService.getGoodsById(id);
        // 新库存等于原库存减去订单商品数
        int stock = goods.getStock() - number;
        // 调用服务层修改库存，若改变行数大于0，说明修改成功
        if (goodsService.changeStock(id, stock) >= 1) {
            // 转发到用户主页
            req.getRequestDispatcher("welcome.user").forward(req, resp);
        }
    }
}
