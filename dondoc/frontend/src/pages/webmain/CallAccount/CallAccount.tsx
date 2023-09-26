import styles from "./CallAccount.module.css";
import BackLogoHeader from "../../toolBox/BackLogoHeader";
import Hana from "../../../assets/image/hana.svg"
import { useState } from "react"


function CallAccount() {
  const [checkList,setCheckList] = useState<string[]>([]);
  const [isChecked,setIsChecked] = useState(false);


  return (
    <div >
      <BackLogoHeader name="계좌 불러오기" left="6rem" top="0.9rem" fontSize="1.8rem" />
        <div className={styles.mainContainer}>
         {/* 여기에 맵함수 */}
          <div className={styles.accountItem}>
            <img className={styles.BankIcon} src={Hana} alt="" />
            <div style={{display:"flex",flexDirection:"column",justifyContent:"center",alignItems:"start"}}>
              <p>계좌이름</p>
              <p><span>은행이름</span>계좌번호</p>
            </div>
          
          </div>
          {/* 여기에 맵함수 */}
        </div>
    </div>
  );
};

export default CallAccount;
