import styles from "./MoimInviteUnit.module.css";
import haaland from "../../../../assets/bbakbbakyee.jpg";
import { useEffect } from "react";
import { useSelector } from "react-redux";
import { UserType } from "../../../../store/slice/userSlice";

interface Props {
  moimMemberId: number,
  inviter: string,
  moimName: string
}

function MoimInviteUnit(props: Props) {

  const userInfo:UserType = useSelector((state:{user:UserType})=>{
    return state.user
  })
  const phoneNumber = userInfo.phoneNumber
  useEffect(() => {
    console.log(userInfo)
  })

  return (
    <div className={styles.container}>
      <div className={styles.content}>
        <div className={styles.banklogo}>
          <img src={haaland} alt="" />
        </div>
        <div className={styles.invitemessage}>
          <p>{props.inviter}님이 {phoneNumber}님을 {props.moimName}모임에 초대하였습니다.</p>
        </div>
        <div className={styles.btns}>
          <button>자세히</button>
        </div>
      </div>
    </div>
  );
}

export default MoimInviteUnit;
