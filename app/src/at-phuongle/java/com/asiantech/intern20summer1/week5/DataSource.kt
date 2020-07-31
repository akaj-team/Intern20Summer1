package com.asiantech.intern20summer1.week5

import com.asiantech.intern20summer1.model.TimelineItem

class DataSource {

    companion object {

        fun createDataSet(): ArrayList<TimelineItem> {
            val list = ArrayList<TimelineItem>()
            list.add(
                TimelineItem(
                    "https://instagram.fdad3-3.fna.fbcdn.net/v/t51.2885-19/s150x150/106703447_931922233991356_6855070879282993668_n.jpg?_nc_ht=instagram.fdad3-3.fna.fbcdn.net&_nc_ohc=GSXdYuEyO2cAX-wTG9Z&oh=e8d354ff6a979c2c490a3310715db9d6&oe=5F49B445",
                    "ryanfoodaholic",
                    "https://instagram.fdad3-1.fna.fbcdn.net/v/t51.2885-15/sh0.08/e35/p640x640/116110346_278472256766967_8049978192802527528_n.jpg?_nc_ht=instagram.fdad3-1.fna.fbcdn.net&_nc_cat=103&_nc_ohc=VpX7NFFr_XYAX-tV6V1&oh=7f8ce9fac8c4119e1df4aabbd051097b&oe=5F49ACCA",
                    616,
                    "Mãi hạnh phúc nhé @hana.foodie @lac.l.a.c.c ơi"
                )
            )
            list.add(
                TimelineItem(
                    "https://instagram.fdad3-3.fna.fbcdn.net/v/t51.2885-19/s150x150/31210856_1973155592937153_878457692639723520_n.jpg?_nc_ht=instagram.fdad3-3.fna.fbcdn.net&_nc_ohc=IEcCJTRo-EQAX9mgxru&oh=c0b581362d753885254a9971d61d1c11&oe=5F49BAE0",
                    "nothingtoeat_",
                    "https://instagram.fdad3-1.fna.fbcdn.net/v/t51.2885-15/sh0.08/e35/p640x640/110328795_308035327207738_1849956288427789743_n.jpg?_nc_ht=instagram.fdad3-1.fna.fbcdn.net&_nc_cat=110&_nc_ohc=6as6m63Yt3gAX-KRn1p&oh=5a0994c1c0375e554b333b795d2a3c60&oe=5F4C7013",
                    1504,
                    "Phát hiện ngay tiệm bánh phô mai bắp ngon ngất ngây nhất định phải thử"
                )
            )
            list.add(
                TimelineItem(
                    "https://instagram.fdad3-3.fna.fbcdn.net/v/t51.2885-19/s150x150/106703447_931922233991356_6855070879282993668_n.jpg?_nc_ht=instagram.fdad3-3.fna.fbcdn.net&_nc_ohc=GSXdYuEyO2cAX-wTG9Z&oh=e8d354ff6a979c2c490a3310715db9d6&oe=5F49B445",
                    "ryanfoodaholic",
                    "https://instagram.fdad3-1.fna.fbcdn.net/v/t51.2885-15/sh0.08/e35/p640x640/115920956_1406354846219410_665162885055980725_n.jpg?_nc_ht=instagram.fdad3-1.fna.fbcdn.net&_nc_cat=110&_nc_ohc=DVyAQZUYOnoAX9E6lze&oh=7b7d18ac1be2c3a81c1aee092ebe44a4&oe=5F4C3258",
                    468,
                    "BỎ TÚI HÀNG BÁNH BÈO TÔM CHẤY CHẤT CHƠI XƠI NO BUỔI CHIỀU \uD83D\uDE1D\uD83D\uDE1D"
                )
            )
            list.add(
                TimelineItem(
                    "https://instagram.fdad3-3.fna.fbcdn.net/v/t51.2885-19/s150x150/68911276_309429299895046_9147851728513662976_n.jpg?_nc_ht=instagram.fdad3-3.fna.fbcdn.net&_nc_ohc=uVsreuYKukkAX8HBSxI&oh=a3b543318c2d9c38bbe7e7ccd5c0f9cb&oe=5F4C9F3C",
                    "finoneder",
                    "https://instagram.fdad3-2.fna.fbcdn.net/v/t51.2885-15/e35/p1080x1080/110328792_117465986494327_2737811355815412734_n.jpg?_nc_ht=instagram.fdad3-2.fna.fbcdn.net&_nc_cat=107&_nc_ohc=OE_62FbbHxYAX_3gC4w&oh=5af74be333b99a973bb8fa3819dfea7d&oe=5F4C9A3B",
                    442,
                    "MÊ MẪN HÀNG ỐC NGON RẺ THẬP CẨM ĐỦ LOẠI ĐỒNG GIÁ 50k Ngay Ga Đà Nẵng \uD83D\uDE0D\uD83D\uDE0D"
                )
            )
            return list
        }
    }
}