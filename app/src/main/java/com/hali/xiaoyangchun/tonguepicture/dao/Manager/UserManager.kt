package com.hali.xiaoyangchun.tonguepicture.dao.Manager

import com.hali.xiaoyangchun.tonguepicture.bean.User
import org.greenrobot.greendao.AbstractDao

class UserManager(dao: AbstractDao<User, Long>) : BaseBeanManager<User, Long>(dao)