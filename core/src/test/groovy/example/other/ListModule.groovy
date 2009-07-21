package example.other

import org.tellurium.dsl.DslContext

/**
 * 
 * @author Jian Fang (John.Jian.Fang@gmail.com)
 *
 * Date: Jul 20, 2009
 * 
 */

public class ListModule extends DslContext {
  public static String LIST_BODY = """
<div class="thumbnails">
    <ul>
        <li class="thumbnail">
            <img alt="Dan Henderson Ruins UFC's Hopes For UFC 105 and Michael Bisping"
                 src="/images_root/image_pictures/0518/0180/127482_crop_85x60.jpg"/>
        </li>
        <li class="thumbnail">
            <img alt="Underdog Sports: Best Pro Women Athletes Today No. 10-6"
                 src="/images_root/image_pictures/0517/9892/98173_crop_85x60.jpg"/>
        </li>
        <li class="thumbnail">
            <img alt="The Chicago Cubs: My First MLB Game Pt.2; The Magic Of Hatfeild Hotdogs!"
                 src="/images_root/image_pictures/0517/9210/100722_crop_85x60.jpg"/>
        </li>
        <li class="thumbnail">
        </li>
        <li class="thumbnail active">
            <img alt="Boston Red Sox Weekly Recap (Week 15)"
                 src="/images_root/image_pictures/0518/0204/6839_crop_85x60.jpg"/>
        </li>
        <li class="thumbnail potd">
            <div class="potd-icon png-fix"/>
            <img alt="Browns Fans Want To See More Of Jerome Harrison, You're Getting Your Wish"
                 src="/images_root/image_pictures/0517/9916/111034_crop_85x60.jpg"/>
        </li>
    </ul>
</div>    
  """

  public void defineUi() {
    ui.Container(uid: "rotator", clocator: [tag: "div", class: "thumbnails"]) {
      List(uid: "tnails", clocator: [tag: "ul"], separator: "li") {
        UrlLink(uid: "all", clocator: [:])
      }
    }
  }
}