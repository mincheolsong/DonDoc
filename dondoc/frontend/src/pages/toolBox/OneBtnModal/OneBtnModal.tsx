import styles from "./OneBtnModal.module.css";


interface OneBtnModal{
  width : string;
  height: string;
  titleText: string;
  title: boolean;
  titleFont:string;
  contentFont: string;
  contentText: string;
  btncolor : string;
  btnTextColor:string;
  btnText:string;
  callback():void;
}




export function OneBtnModal(props:OneBtnModal){
  return(
    <div className={styles.mainBox} style={{display:"flex",justifyContent:"center"}}>
      <div className={styles.nomalBox} style={{width:props.width, height: props.height}}>
       {props.title ? <p  style={{fontSize:props.titleFont, marginTop:"0" ,fontWeight:"bold"}}>{props.titleText}</p> : ''}
        <p  style={{fontSize:props.contentFont, fontWeight:"bold"}}>{props.contentText}</p>
        <div style={{marginTop:"8%", width:"68%", display:"flex", justifyContent:"center"}}>
          <button
          onClick= {props.callback}
           className={styles.modalButton} style={{backgroundColor:props.btncolor,color:props.btnTextColor,fontSize:"1rem",fontWeight:'bold'}}>{props.btnText}</button>
        </div>
      </div> 
    </div>
  )
}

export default OneBtnModal;
