package com.girlkun.models.event.events;

import com.girlkun.models.event.Event;

/**
 * Default event - chế độ bình thường khi không có sự kiện nào active
 *
 * @author Umi Team
 */
public class Default extends Event {

    @Override
    public void init() {
        // Không làm gì - chế độ bình thường
    }
}
