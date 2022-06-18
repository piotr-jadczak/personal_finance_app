import 'bootstrap/dist/css/bootstrap.css';

const Label = (props) => {

  const title = props.title

  return (
    <div className="card">
      <div className="card-header">
        {title}
      </div>
    </div>
  )
}

export default Label