import './profitDisplayer.css';
import 'bootstrap/dist/css/bootstrap.css';

const ProfitDisplayer = (props) => {

  const profit = Math.round(props.profit * 100) / 100
  const description = props.description
  const profitPercent = "(" +  (Math.round(props.profitPercent * 100) / 100) + "%)"

  const textClass = () => {
    if(profit > 0.0)
      return "text-success"
    if(profit < 0.0)
      return "text-danger"
    return "text-secondary"    
  }

  return (
    <div>
      <div className="total-money">
        <br />
        <span className={textClass()} >
          <a> {profit + " zł"}</a>
          <span className="total-money-percent">
            <a> {profitPercent} </a>
          </span>
        </span>
      </div>
      <div className="total_underscore">
        <hr style={{margin: 0}} />
        {description}
      </div>
    </div>
  )
}

export default ProfitDisplayer