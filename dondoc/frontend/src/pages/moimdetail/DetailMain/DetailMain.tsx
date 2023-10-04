import styles from "./DetailMain.module.css";
import DetailFirst from "../DetailFirst/DetailFirst"
import DetailSecond from "../DetailSecond/DetailSecond"
import DetailThird from "../DetailThird/DetailThird"
import { useLocation } from "react-router-dom";


function DetailMain() {

  const { state } = useLocation()
  const userType = state.userType
  const accountId = state.accountId

  return (
    <div className={styles.container}>
      <DetailFirst userType={userType} accountId={accountId}/>
      <DetailSecond />
      <DetailThird />
    </div>
  );
}

export default DetailMain;
