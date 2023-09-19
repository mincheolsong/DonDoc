import styles from "./Signup.module.css";
import dondoc from '../../../assets/image/dondocLogo.png'



interface SignProps {
  name: string;
  setting: any;
  type:string;
}



function Signup() {
  return (
    <div>
      <div className={styles.mainContainer}>
        <img className={styles.Logo} src={dondoc} />
      </div>
  
        <div className={styles.mainContainerBottom}>
            <SignUpInput type='text' name='전화번호' setting='onPhone'/>
            <SignUpInput type='text' name='이름' setting='onPhone'/>
            <SignUpInput type='password' name='비밀번호' setting='onPhone'/>
            <SignUpInput type='password' name='비밀번호 확인' setting='onPhone'/>
            <SignUpInput type='text' name='이메일' setting='onPhone'/>
            <SignUpInput type='text' name='닉네임' setting='onPhone'/>
        </div>
      <div className={styles.btnBox} >
        <button className={styles.signUpBtn}>회원가입</button>
      </div>
    </div>    
  );
}


function SignUpInput(props:SignProps){
  return(
    <input className={styles.IdBox} type={props.type} placeholder={props.name} onChange={props.setting}/>
  )
}
export default Signup;
