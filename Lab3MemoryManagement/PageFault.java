import java.util.*;


public class PageFault {

  private static void sortByTime(List<Page> mem){
    Collections.sort(mem, new Comparator<Page>() {

      @Override
      public int compare(Page p1, Page p2) {
        if(p1.inMemTime == p2.inMemTime){return 0;}
        else if(p1.inMemTime< p2.inMemTime){ return 1;}
        else {return -1;}
      }
    });
  }
  public static void replacePageRoundRobin(List<Page> mem, int virtPageNum, int replacePageNum, ControlPanel controlPanel) {
    //sortByTime(mem);
    int oldestTime = 0;
    int indexToRemove = 0;
    boolean checkR = true;

    for(ListIterator<Page> page = mem.listIterator(); page.hasNext(); ){
      Page nextPage = page.next();
      int index = page.nextIndex() -1;

      if(mem.get(index).R==1) {
        mem.get(index - 1).R=0;
      } else {
        checkR=false;
        if(mem.get(index).inMemTime > oldestTime) {
          oldestTime = mem.get(index).inMemTime;
          indexToRemove = index;
        }
      }
      if(checkR==true && nextPage.equals(mem.listIterator(mem.size()-1))){
        page = mem.listIterator(0);
      }
    }
    Page page = (Page) mem.get(indexToRemove);
    Page nextpage = (Page) mem.get(replacePageNum);
    controlPanel.removePhysicalPage(indexToRemove);
    nextpage.physical = page.physical;
    controlPanel.addPhysicalPage(nextpage.physical, replacePageNum);
    page.inMemTime = 0;
    page.lastTouchTime = 0;
    page.R = 0;
    page.M = 0;
    page.physical = -1;
  }
}

