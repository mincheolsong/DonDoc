import {useState} from "react";
import styles from "./RequestModal.module.css";
import haaland from "../../../../assets/bbakbbakyee.jpg"

type Props = {
  setModalOpen(id: boolean) : void
}


function RequestModal({setModalOpen}: Props) {

  const [nowSelected, setNowSelected] = useState<boolean>(true)

  const ClickMissionTab = () => {
    setNowSelected(false)
    // console.log(nowSelected)
  }

  const ClickMoneyTab = () => {
    setNowSelected(true)
    // console.log(nowSelected)
  }

  const ModalClose = () => {
    setModalOpen(false)
  }


  return (
    <div className={styles.container}>
      <div className={styles.content}>

        <div className={styles.toptab}>
          <div className={styles.requestmoney} onClick={ClickMoneyTab}>
            <div className={styles.icon}>
              <img src={haaland} alt="" />
            </div>
            <div className={styles.requesttext}>
              <p style={{color: nowSelected ? '#7677E8' : '', borderBottom: nowSelected ? '2px solid #7677E8' : ''}}>요청하기</p>
            </div>
          </div>

          <div className={styles.requestmission} onClick={ClickMissionTab}>
            <div className={styles.icon}>
              <img src={haaland} alt="" />
            </div>
            <div className={styles.requesttext}>
              <p style={{color: !nowSelected ? '#DD7979' : '', borderBottom: !nowSelected ? '2px solid #DD7979' : ''}}>미션등록</p>
            </div>
          </div>
        </div>


        <div className={styles.selectcontent}>
          {nowSelected ? (
            <div className={styles.inputs}>
            <div className={styles.requestname}>
              <input type="text" placeholder="요청명" />
            </div>
            <div className={styles.requestname}>
              <input type="text" placeholder="금액(원)" />
            </div>
            <div className={styles.requestname}>
              <input type="text" placeholder="요청상세" />
            </div>
            <div className={styles.requestname}>
              <select name="category" id="category">
                <option value="category">카테고리</option>
                <option value="americano">아메리카노</option>
                <option value="caffe latte">카페라테</option>
                <option value="cafe au lait">카페오레</option>
                <option value="espresso">에스프레소</option>
              </select>
            </div>

            <div className={styles.btns}>
              <button onClick={ModalClose}>닫기</button>
              <button>요청하기</button>
            </div>
          </div>
          ) : (
            <div className={styles.inputs}>
            <div className={styles.requestname}>
              <input type="text" placeholder="미션명" />
            </div>
            <div className={styles.requestname}>
              <input type="text" placeholder="금액(원)" />
            </div>
            <div className={styles.requestname}>
              <input type="text" placeholder="요청상세" />
            </div>
            <div className={styles.requestname}>
              <label htmlFor="">종료일자</label>
              <input type="date" />
            </div>

            <div className={styles.btns}>
              <button onClick={ModalClose}>닫기</button>
              <button>등록하기</button>
            </div>
          </div>
          )}

        </div>



      </div>
    </div>
  );
};

export default RequestModal;
