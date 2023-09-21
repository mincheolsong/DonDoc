import styles from "./Home.module.css";
import Nav from "../../Nav";
import Header from "../Header";
import peter from "../../../assets/image/peter.svg"
import { NavLink } from "react-router-dom";
interface Props {

}

function Home(props: Props) {
  return (
    <div className={styles.container}>
      <Header/>
      <div style={{display:"flex" , flexDirection:"column",  alignItems:"center", marginTop:"1.5rem"}}>
    <div className={styles.topContainer}>
      <img src={peter} style={{width:"20%"}} />
      <div style={{marginLeft:"1rem",textAlign:"center"}}>
        <p>user name 의 DonDoc</p>
        <button className={styles.myProfileBtn}> 나의프로필가기</button>
      </div>
      <div style={{marginLeft:"3rem"}}>
        <button style={{fontSize:""}}> 계좌 개설하기</button>
      </div>
    </div>


    <div style={{display:"flex",flexDirection:"row",justifyContent:"space-between",alignItems:"end",width:"98%",marginTop:"1rem"}}>
    <p style={{fontSize:"2.2rem",fontWeight:"bold", marginBottom:"1rem"}}>나의 계좌</p>
    <button className={styles.accountBtn}>계좌불러오기</button>
    </div>
    
    <div className={styles.midContainer}>
      나의 계좌     버튼
    </div>
    



      <Nav/>
      </div>
    </div>
  );
}

export default Home;


export function accountItem(){
  return(
    <div>

    </div>
  )
}
