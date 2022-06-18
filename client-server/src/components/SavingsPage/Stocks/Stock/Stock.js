import './stock.css'
import { BsXSquare } from 'react-icons/bs'
import { BsPencilFill } from 'react-icons/bs'

const Stock = (props) => {

  const id = props.stock.id;
  const quantity = props.stock.quantity;
  const avgBought = props.stock.avgBought;
  const currentPrice = props.stock.stockData.currentPrice;
  const symbol = props.stock.stockData.symbol;
  const companyName = props.stock.stockData.companyName;
  const fetchTime = props.stock.stockData.fetchTime;
  const profit = Math.round((currentPrice-avgBought) * quantity * 100) / 100;

  const textClass = () => {
    if(profit > 0)
      return "text-success no-"
    if(profit < 0)
      return "text-danger"
    return "text-secondary"    
  }

  const onDeleteButtonClick = () => {
    props.onDelete(id)
  }

  const onUpdateButtonClick = () => {
    props.onUpdateForm(id, {quantity : quantity, avgBought : avgBought, companyName : companyName })
  }

  return (
    <div className="col-sm-3">
    <div className='currency'>
      <div className='stock-buttons-holder'>
        <BsPencilFill size={20} className='stock-edit-button' onClick={onUpdateButtonClick} />
        <BsXSquare size={24} className='stock-delete-button' onClick={onDeleteButtonClick} />
      </div>
      <a className='stock-price'>{currentPrice + "zł"}</a><br/>
      <a className='stock-name'>{companyName + " (" + symbol + ")" }</a><br/><br/>
      <a>{avgBought+ " zł"}</a><br/>
      <a className='min-descriptor'>avg bougth</a><br/>
      <a>{quantity}</a><br/>
      <a className='min-descriptor'>quantity</a><br/>
      <a className={textClass() + ' text-decoration-none'}>{profit+ " zł"}</a><br/>
      <a className='min-descriptor'>profit</a>
      <div className='stock-time-holder'>
        <a className='stock-fetch-time'>{ fetchTime.slice(0, -3) }</a>
      </div>
    </div>
  </div>
  )
}

export default Stock